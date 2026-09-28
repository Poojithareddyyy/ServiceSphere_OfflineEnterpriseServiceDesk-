package servicesphere_Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import JarJdbc.DBConn;

import java.util.ArrayList;
import java.util.List;

public class TicketService {

    public String createTicket(
            String username,
            String issueTitle,
            String category,
            String priority,
            String asset,
            String description) {

        Connection con = null;
        PreparedStatement userStmt = null;
        PreparedStatement ticketStmt = null;
        PreparedStatement slaStmt = null;
        ResultSet rs = null;

        try {

            con = DBConn.MyConnection();

            // =========================================
            // 1. GET USER ID
            // =========================================

            String userSql =
                    "SELECT user_id FROM users WHERE username = ?";

            userStmt = con.prepareStatement(userSql);
            userStmt.setString(1, username);

            rs = userStmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            int userId = rs.getInt("user_id");

            rs.close();
            userStmt.close();

            // =========================================
            // 2. GET ASSET ID
            // =========================================

            Integer assetId = null;

            if (!asset.equals("Select Asset")
                    && !asset.equals("Other")) {

                String assetCode = asset.substring(
                        asset.lastIndexOf("-") + 1
                ).trim();

                String assetSql =
                        "SELECT asset_id FROM assets WHERE asset_code = ?";

                PreparedStatement assetStmt =
                        con.prepareStatement(assetSql);

                assetStmt.setString(1, assetCode);

                ResultSet assetRs =
                        assetStmt.executeQuery();

                if (assetRs.next()) {
                    assetId = assetRs.getInt("asset_id");
                }

                assetRs.close();
                assetStmt.close();
            }

            // =========================================
            // 3. GENERATE TEMPORARY TICKET NUMBER
            // =========================================

            String ticketNumber =
                    "INC-" + System.currentTimeMillis();

            // =========================================
            // 4. INSERT TICKET
            // =========================================

            String ticketSql =
                    "INSERT INTO tickets " +
                    "(ticket_number, created_by, asset_id, " +
                    "issue_title, category, priority, description, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, 'OPEN')";

            ticketStmt = con.prepareStatement(
                    ticketSql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ticketStmt.setString(1, ticketNumber);
            ticketStmt.setInt(2, userId);

            if (assetId == null) {
                ticketStmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                ticketStmt.setInt(3, assetId);
            }

            ticketStmt.setString(4, issueTitle);
            ticketStmt.setString(5, category);
            ticketStmt.setString(6, priority);
            ticketStmt.setString(7, description);

            int result = ticketStmt.executeUpdate();

            if (result == 0) {
                return null;
            }

            // =========================================
            // 5. GET GENERATED TICKET ID
            // =========================================

            ResultSet generatedKeys =
                    ticketStmt.getGeneratedKeys();

            int ticketId = 0;

            if (generatedKeys.next()) {
                ticketId = generatedKeys.getInt(1);
            }

            generatedKeys.close();

            // =========================================
            // 6. CALCULATE SLA
            // =========================================

            int targetMinutes;

            switch (priority) {

                case "Critical":
                    targetMinutes = 60;
                    break;

                case "High":
                    targetMinutes = 240;
                    break;

                case "Medium":
                    targetMinutes = 480;
                    break;

                default:
                    targetMinutes = 1440;
                    break;
            }

            // =========================================
            // 7. CREATE SLA RECORD
            // =========================================

            String slaSql =
                    "INSERT INTO sla " +
                    "(ticket_id, target_minutes, due_at) " +
                    "VALUES (?, ?, DATE_ADD(NOW(), INTERVAL ? MINUTE))";

            slaStmt = con.prepareStatement(slaSql);

            slaStmt.setInt(1, ticketId);
            slaStmt.setInt(2, targetMinutes);
            slaStmt.setInt(3, targetMinutes);

            slaStmt.executeUpdate();

            return ticketNumber;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            try {
                if (rs != null) rs.close();
                if (userStmt != null) userStmt.close();
                if (ticketStmt != null) ticketStmt.close();
                if (slaStmt != null) slaStmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public List<Object[]> getMyTickets(String username) {

        List<Object[]> tickets = new ArrayList<>();

        String sql =
                "SELECT " +
                "t.ticket_number, " +
                "t.issue_title, " +
                "t.category, " +
                "t.priority, " +
                "t.status, " +
                "t.created_at, " +
                "s.due_at, " +
                "CASE " +
                "   WHEN t.status = 'CLOSED' THEN 'CLOSED' " +
                "   WHEN t.status = 'RESOLVED' THEN 'RESOLVED' " +
                "   WHEN s.breached = TRUE OR NOW() > s.due_at THEN 'BREACHED' " +
                "   ELSE 'WITHIN SLA' " +
                "END AS sla_status " +
                "FROM tickets t " +
                "JOIN users u ON t.created_by = u.user_id " +
                "LEFT JOIN sla s ON t.ticket_id = s.ticket_id " +
                "WHERE u.username = ? " +
                "ORDER BY t.created_at DESC";

        try {

            Connection con = DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Object[] row = {
                    rs.getString("ticket_number"),
                    rs.getString("issue_title"),
                    rs.getString("category"),
                    rs.getString("priority"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("due_at"),
                    rs.getString("sla_status")
                };

                tickets.add(row);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return tickets;
    }
    
    public int getMyTicketCount(String username) {

        String sql =
                "SELECT COUNT(*) " +
                "FROM tickets t " +
                "JOIN users u ON t.created_by = u.user_id " +
                "WHERE u.username = ?";

        try {

            Connection con = DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                int count = rs.getInt(1);

                rs.close();
                pst.close();
                con.close();

                return count;
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
    
    public int getMyOpenTicketCount(String username) {

        String sql =
                "SELECT COUNT(*) " +
                "FROM tickets t " +
                "JOIN users u ON t.created_by = u.user_id " +
                "WHERE u.username = ? " +
                "AND t.status = 'OPEN'";

        try {

            Connection con = DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                int count = rs.getInt(1);

                rs.close();
                pst.close();
                con.close();

                return count;
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
    
    public int getMyResolvedTicketCount(String username) {

        String sql =
                "SELECT COUNT(*) " +
                "FROM tickets t " +
                "JOIN users u ON t.created_by = u.user_id " +
                "WHERE u.username = ? " +
                "AND t.status = 'RESOLVED'";

        try {

            Connection con = DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                int count = rs.getInt(1);

                rs.close();
                pst.close();
                con.close();

                return count;
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
    
 // ==========================================
 // GET ALL TICKETS FOR ADMIN
 // ==========================================

 public java.util.List<Object[]> getAllTickets() {

     java.util.List<Object[]> tickets =
             new java.util.ArrayList<>();

     String sql =
    	        "SELECT " +
    	        "t.ticket_id, " +
    	        "t.ticket_number, " +
    	        "u.full_name AS created_by_name, " +
    	        "t.issue_title, " +
    	        "t.category, " +
    	        "t.priority, " +
    	        "t.status, " +
    	        "t.created_at, " +
    	        "a.asset_code, " +
    	        "s.breached, " +
    	        "tech.technician_name AS technician_name " +
    	        "FROM tickets t " +
    	        "JOIN users u " +
    	        "ON t.created_by = u.user_id " +
    	        "LEFT JOIN assets a " +
    	        "ON t.asset_id = a.asset_id " +
    	        "LEFT JOIN sla s " +
    	        "ON t.ticket_id = s.ticket_id " +
    	        "LEFT JOIN ticket_assignment ta " +
    	        "ON t.ticket_id = ta.ticket_id " +
    	        "LEFT JOIN technicians tech " +
    	        "ON ta.technician_id = tech.technician_id " +
    	        "ORDER BY t.created_at DESC";

     try {

         java.sql.Connection con =
                 JarJdbc.DBConn.MyConnection();

         java.sql.PreparedStatement pst =
                 con.prepareStatement(sql);

         java.sql.ResultSet rs =
                 pst.executeQuery();

         while (rs.next()) {

             Object[] row = {
                     rs.getInt("ticket_id"),
                     rs.getString("ticket_number"),
                     rs.getString("created_by_name"),
                     rs.getString("issue_title"),
                     rs.getString("category"),
                     rs.getString("priority"),
                     rs.getString("status"),
                     rs.getTimestamp("created_at"),
                     rs.getString("asset_code"),
                     rs.getBoolean("breached"),
                     rs.getString("technician_name")
             };

             tickets.add(row);
         }

         rs.close();
         pst.close();
         con.close();

     } catch (Exception e) {

         e.printStackTrace();
     }

     return tickets;
 }
 
//==========================================
//GET AVAILABLE TECHNICIANS
//==========================================

public java.util.List<Object[]> getAvailableTechnicians() {

  java.util.List<Object[]> technicians =
          new java.util.ArrayList<>();

  String sql =
          "SELECT technician_id, technician_name, " +
          "specialization, status " +
          "FROM technicians " +
          "WHERE status = 'AVAILABLE' " +
          "ORDER BY technician_name";

  try {

      java.sql.Connection con =
              JarJdbc.DBConn.MyConnection();

      java.sql.PreparedStatement pst =
              con.prepareStatement(sql);

      java.sql.ResultSet rs =
              pst.executeQuery();

      while (rs.next()) {

          Object[] row = {
                  rs.getInt("technician_id"),
                  rs.getString("technician_name"),
                  rs.getString("specialization"),
                  rs.getString("status")
          };

          technicians.add(row);
      }

      rs.close();
      pst.close();
      con.close();

  } catch (Exception e) {

      e.printStackTrace();
  }

  return technicians;
}

//==========================================
//ASSIGN TECHNICIAN TO TICKET
//==========================================

//==========================================
//ASSIGN / REASSIGN TECHNICIAN TO TICKET
//==========================================

public boolean assignTechnician(
     int ticketId,
     int technicianId,
     int assignedBy) {

 java.sql.Connection con = null;
 java.sql.PreparedStatement deleteStmt = null;
 java.sql.PreparedStatement insertStmt = null;
 java.sql.PreparedStatement statusStmt = null;

 try {

     con = JarJdbc.DBConn.MyConnection();

     // ------------------------------------------
     // Start transaction
     // ------------------------------------------

     con.setAutoCommit(false);


     // ------------------------------------------
     // Remove previous current assignment
     // ------------------------------------------

     String deleteSql =
             "DELETE FROM ticket_assignment " +
             "WHERE ticket_id = ?";

     deleteStmt =
             con.prepareStatement(deleteSql);

     deleteStmt.setInt(1, ticketId);

     deleteStmt.executeUpdate();


     // ------------------------------------------
     // Insert new assignment
     // ------------------------------------------

     String insertSql =
             "INSERT INTO ticket_assignment " +
             "(ticket_id, technician_id, assigned_by) " +
             "VALUES (?, ?, ?)";

     insertStmt =
             con.prepareStatement(insertSql);

     insertStmt.setInt(1, ticketId);
     insertStmt.setInt(2, technicianId);
     insertStmt.setInt(3, assignedBy);

     int assignmentResult =
             insertStmt.executeUpdate();


     if (assignmentResult == 0) {

         con.rollback();

         return false;
     }


     // ------------------------------------------
     // Change ticket status to ASSIGNED
     // ------------------------------------------

     String statusSql =
             "UPDATE tickets " +
             "SET status = 'ASSIGNED' " +
             "WHERE ticket_id = ?";

     statusStmt =
             con.prepareStatement(statusSql);

     statusStmt.setInt(1, ticketId);

     statusStmt.executeUpdate();


     // ------------------------------------------
     // Commit everything
     // ------------------------------------------

     con.commit();

     return true;


 } catch (Exception e) {

     e.printStackTrace();

     try {

         if (con != null) {
             con.rollback();
         }

     } catch (Exception rollbackException) {

         rollbackException.printStackTrace();
     }

     return false;


 } finally {

     try {
         if (deleteStmt != null) {
             deleteStmt.close();
         }

         if (insertStmt != null) {
             insertStmt.close();
         }

         if (statusStmt != null) {
             statusStmt.close();
         }

         if (con != null) {
             con.setAutoCommit(true);
             con.close();
         }

     } catch (Exception closeException) {

         closeException.printStackTrace();
     }
 }
}

//==========================================
//GET ADMIN USER ID
//==========================================

public int getAdminUserId() {

 String sql =
         "SELECT user_id " +
         "FROM users " +
         "WHERE role = 'ADMIN' " +
         "AND status = 'ACTIVE' " +
         "LIMIT 1";

 try {

     java.sql.Connection con =
             JarJdbc.DBConn.MyConnection();

     java.sql.PreparedStatement pst =
             con.prepareStatement(sql);

     java.sql.ResultSet rs =
             pst.executeQuery();

     if (rs.next()) {

         int adminId =
                 rs.getInt("user_id");

         rs.close();
         pst.close();
         con.close();

         return adminId;
     }

     rs.close();
     pst.close();
     con.close();

 } catch (Exception e) {

     e.printStackTrace();
 }

 return -1;
}
   
//==========================================
//GET SLA MONITOR DATA
//==========================================

public java.util.List<Object[]> getSLAMonitorData() {

 java.util.List<Object[]> slaList =
         new java.util.ArrayList<>();

 String sql =
         "SELECT " +
         "t.ticket_id, " +
         "t.ticket_number, " +
         "u.full_name AS created_by_name, " +
         "t.priority, " +
         "t.status, " +
         "s.started_at, " +
         "s.due_at, " +
         "s.breached, " +
         "s.breached_at, " +
         "tech.technician_name " +
         "FROM tickets t " +
         "JOIN users u " +
         "ON t.created_by = u.user_id " +
         "JOIN sla s " +
         "ON t.ticket_id = s.ticket_id " +
         "LEFT JOIN ticket_assignment ta " +
         "ON t.ticket_id = ta.ticket_id " +
         "LEFT JOIN technicians tech " +
         "ON ta.technician_id = tech.technician_id " +
         "ORDER BY s.due_at ASC";

 try {

     java.sql.Connection con =
             JarJdbc.DBConn.MyConnection();

     java.sql.PreparedStatement pst =
             con.prepareStatement(sql);

     java.sql.ResultSet rs =
             pst.executeQuery();

     while (rs.next()) {

         java.sql.Timestamp startedAt =
                 rs.getTimestamp("started_at");

         java.sql.Timestamp dueAt =
                 rs.getTimestamp("due_at");

         boolean breached =
                 rs.getBoolean("breached");

         String slaStatus;

         if (breached) {

             slaStatus = "BREACHED";

         } else if (
                 dueAt != null
                 && dueAt.getTime()
                 <= System.currentTimeMillis()) {

             slaStatus = "BREACHED";

         } else if (
                 dueAt != null
                 && (dueAt.getTime()
                 - System.currentTimeMillis())
                 <= 60 * 60 * 1000) {

             slaStatus = "WARNING";

         } else {

             slaStatus = "WITHIN SLA";
         }


         long remainingMillis = 0;

         if (dueAt != null) {

             remainingMillis =
                     dueAt.getTime()
                     - System.currentTimeMillis();
         }


         long remainingMinutes =
                 remainingMillis / (60 * 1000);


         Object[] row = {

                 rs.getInt("ticket_id"),

                 rs.getString(
                         "ticket_number"
                 ),

                 rs.getString(
                         "created_by_name"
                 ),

                 rs.getString(
                         "priority"
                 ),

                 rs.getString(
                         "status"
                 ),

                 startedAt,

                 dueAt,

                 remainingMinutes,

                 slaStatus,

                 rs.getString(
                         "technician_name"
                 )
         };

         slaList.add(row);
     }

     rs.close();
     pst.close();
     con.close();

 } catch (Exception e) {

     e.printStackTrace();
 }

 return slaList;
}

public boolean updateTicketStatus(int ticketId, String status) {

    Connection con = null;
    PreparedStatement pst = null;

    try {

        con = DBConn.MyConnection();

        String sql;

        if (status.equals("RESOLVED")) {

            sql =
                "UPDATE tickets " +
                "SET status = ?, resolved_at = NOW() " +
                "WHERE ticket_id = ?";

        } else if (status.equals("CLOSED")) {

            sql =
                "UPDATE tickets " +
                "SET status = ?, closed_at = NOW() " +
                "WHERE ticket_id = ?";

        } else {

            sql =
                "UPDATE tickets " +
                "SET status = ? " +
                "WHERE ticket_id = ?";
        }

        pst = con.prepareStatement(sql);

        pst.setString(1, status);
        pst.setInt(2, ticketId);

        int result = pst.executeUpdate();

        return result > 0;

    } catch (Exception e) {

        e.printStackTrace();
        return false;

    } finally {

        try {

            if (pst != null) {
                pst.close();
            }

            if (con != null) {
                con.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    
}