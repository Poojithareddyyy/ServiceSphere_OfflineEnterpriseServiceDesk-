package servicesphere_Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import JarJdbc.DBConn;
 
public class AdminDashboardService {

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



    public int getActiveTickets() {

        String sql =
                "SELECT COUNT(*) FROM tickets " +
                "WHERE status IN " +
                "('OPEN', 'ASSIGNED', 'IN PROGRESS', 'WAITING')";

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


    public int getHighPriorityTickets() {

        String sql =
                "SELECT COUNT(*) FROM tickets " +
                "WHERE priority = 'High'";

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



    public int getTechnicianCount() {

        String sql =
                "SELECT COUNT(*) FROM technicians";

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


    public List<Object[]> getRecentTickets() {

        List<Object[]> tickets = new ArrayList<>();

        String sql =
                "SELECT ticket_number, issue_title, status " +
                "FROM tickets " +
                "ORDER BY created_at DESC " +
                "LIMIT 3";

        try {
            Connection con = DBConn.MyConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Object[] row = {
                    rs.getString("ticket_number"),
                    rs.getString("issue_title"),
                    rs.getString("status")
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
}
