package servicesphere_Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import JarJdbc.DBConn;

public class ReportService {

    public int getTotalTickets() {

        String sql = "SELECT COUNT(*) FROM tickets";

        try {
            Connection con = DBConn.MyConnection();
            PreparedStatement pst = con.prepareStatement(sql);
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


    public int getOpenTickets() {

        String sql =
            "SELECT COUNT(*) FROM tickets " +
            "WHERE status = 'OPEN'";

        try {
            Connection con = DBConn.MyConnection();
            PreparedStatement pst = con.prepareStatement(sql);
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


    public int getAssignedTickets() {

        String sql =
            "SELECT COUNT(*) FROM tickets " +
            "WHERE status = 'ASSIGNED'";

        try {
            Connection con = DBConn.MyConnection();
            PreparedStatement pst = con.prepareStatement(sql);
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


    public int getInProgressTickets() {

        String sql =
            "SELECT COUNT(*) FROM tickets " +
            "WHERE status = 'IN PROGRESS'";

        try {
            Connection con = DBConn.MyConnection();
            PreparedStatement pst = con.prepareStatement(sql);
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


    public int getResolvedTickets() {

        String sql =
            "SELECT COUNT(*) FROM tickets " +
            "WHERE status = 'RESOLVED'";

        try {
            Connection con = DBConn.MyConnection();
            PreparedStatement pst = con.prepareStatement(sql);
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


    public int getClosedTickets() {

        String sql =
            "SELECT COUNT(*) FROM tickets " +
            "WHERE status = 'CLOSED'";

        try {
            Connection con = DBConn.MyConnection();
            PreparedStatement pst = con.prepareStatement(sql);
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


    public int getSLABreachedTickets() {

        String sql =
            "SELECT COUNT(*) FROM sla " +
            "WHERE breached = TRUE";

        try {
            Connection con = DBConn.MyConnection();
            PreparedStatement pst = con.prepareStatement(sql);
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
    
    public java.util.List<Object[]> getTechnicianWorkload() {

        java.util.List<Object[]> workload =
                new java.util.ArrayList<>();

        String sql =
            "SELECT " +
            "tech.technician_name, " +
            "COUNT(ta.ticket_id) AS total_tickets, " +
            "SUM(CASE WHEN t.status = 'RESOLVED' " +
            "THEN 1 ELSE 0 END) AS resolved_tickets, " +
            "SUM(CASE WHEN t.status NOT IN " +
            "('RESOLVED', 'CLOSED') " +
            "THEN 1 ELSE 0 END) AS active_tickets " +
            "FROM technicians tech " +
            "LEFT JOIN ticket_assignment ta " +
            "ON tech.technician_id = ta.technician_id " +
            "LEFT JOIN tickets t " +
            "ON ta.ticket_id = t.ticket_id " +
            "GROUP BY tech.technician_id, tech.technician_name " +
            "ORDER BY total_tickets DESC";

        try {

            java.sql.Connection con =
                    JarJdbc.DBConn.MyConnection();

            java.sql.PreparedStatement pst =
                    con.prepareStatement(sql);

            java.sql.ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Object[] row = {
                    rs.getString("technician_name"),
                    rs.getInt("total_tickets"),
                    rs.getInt("resolved_tickets"),
                    rs.getInt("active_tickets")
                };

                workload.add(row);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return workload;
    }
}