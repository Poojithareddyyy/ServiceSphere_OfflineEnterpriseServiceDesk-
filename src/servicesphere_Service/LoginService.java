package servicesphere_Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import JarJdbc.DBConn;

public class LoginService {

    public String authenticate(
            String username,
            String password,
            String role) {

        String sql =
                "SELECT role FROM users " +
                "WHERE username = ? " +
                "AND password = ? " +
                "AND role = ? " +
                "AND status = 'ACTIVE'";

        try {

            Connection con = DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {

                return rs.getString("role");
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Database/Login Error:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return null;
    }
}