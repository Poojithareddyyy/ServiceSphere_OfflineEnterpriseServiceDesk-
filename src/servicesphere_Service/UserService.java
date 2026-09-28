package servicesphere_Service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import JarJdbc.DBConn;

public class UserService {

    public boolean addEmployee(
            String fullName,
            String username,
            String email,
            String password,
            String department,
            String phone) {

        String sql =
                "INSERT INTO users " +
                "(full_name, username, email, password, role, department, phone) " +
                "VALUES (?, ?, ?, ?, 'EMPLOYEE', ?, ?)";

        try {

            Connection con =
                    DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, fullName);
            pst.setString(2, username);
            pst.setString(3, email);
            pst.setString(4, password);
            pst.setString(5, department);
            pst.setString(6, phone);

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