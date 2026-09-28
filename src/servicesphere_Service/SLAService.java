package servicesphere_Service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import JarJdbc.DBConn;

public class SLAService {

    public void processSLABreaches() {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            con = DBConn.MyConnection();

            String sql =
                "UPDATE sla s " +
                "JOIN tickets t ON s.ticket_id = t.ticket_id " +
                "SET " +
                "s.breached = TRUE, " +
                "s.breached_at = NOW(), " +
                "s.escalated = TRUE, " +
                "s.escalated_at = NOW() " +
                "WHERE s.breached = FALSE " +
                "AND s.due_at <= NOW() " +
                "AND t.status NOT IN ('RESOLVED', 'CLOSED')";

            pst = con.prepareStatement(sql);

            int updated = pst.executeUpdate();

            System.out.println(
                "SLA breach check completed. Records updated: "
                + updated
            );

        } catch (Exception e) {

            e.printStackTrace();

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