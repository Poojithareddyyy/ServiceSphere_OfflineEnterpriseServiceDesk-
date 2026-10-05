package servicesphere_Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import JarJdbc.DBConn;

public class TechnicianService {


    public List<Object[]> getAllTechnicians() {

        List<Object[]> technicians =
                new ArrayList<>();

        String sql =
                "SELECT technician_id, technician_name, " +
                "email, specialization, phone, status " +
                "FROM technicians " +
                "ORDER BY technician_name";

        try {

            Connection con =
                    DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Object[] row = {
                        rs.getInt("technician_id"),
                        rs.getString("technician_name"),
                        rs.getString("email"),
                        rs.getString("specialization"),
                        rs.getString("phone"),
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



    public boolean addTechnician(
            String technicianName,
            String email,
            String specialization,
            String phone) {

        String sql =
                "INSERT INTO technicians " +
                "(technician_name, email, specialization, phone) " +
                "VALUES (?, ?, ?, ?)";

        try {

            Connection con =
                    DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, technicianName);
            pst.setString(2, email);
            pst.setString(3, specialization);
            pst.setString(4, phone);

            int result =
                    pst.executeUpdate();

            pst.close();
            con.close();

            return result > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }



    public boolean updateTechnicianStatus(
            int technicianId,
            String status) {

        String sql =
                "UPDATE technicians " +
                "SET status = ? " +
                "WHERE technician_id = ?";

        try {

            Connection con =
                    DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, status);
            pst.setInt(2, technicianId);

            int result =
                    pst.executeUpdate();

            pst.close();
            con.close();

            return result > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}
