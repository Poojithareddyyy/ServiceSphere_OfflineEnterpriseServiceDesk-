package servicesphere_Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import JarJdbc.DBConn;

public class AssetService {

    public List<Object[]> getMyAssets(String username) {

        List<Object[]> assets = new ArrayList<>();

        String sql =
                "SELECT " +
                "a.asset_code, " +
                "a.asset_name, " +
                "a.asset_type, " +
                "a.serial_number, " +
                "a.status " +
                "FROM assets a " +
                "JOIN users u " +
                "ON a.assigned_user_id = u.user_id " +
                "WHERE u.username = ? " +
                "ORDER BY a.asset_code";

        try {

            Connection con = DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Object[] row = {
                    rs.getString("asset_code"),
                    rs.getString("asset_name"),
                    rs.getString("asset_type"),
                    rs.getString("serial_number"),
                    rs.getString("status")
                };

                assets.add(row);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return assets;
    }
    
 // ==========================================
 // GET ALL ASSETS FOR ADMIN
 // ==========================================

 public List<Object[]> getAllAssets() {

     List<Object[]> assets = new ArrayList<>();

     String sql =
             "SELECT " +
             "a.asset_id, " +
             "a.asset_code, " +
             "a.asset_name, " +
             "a.asset_type, " +
             "a.serial_number, " +
             "a.status, " +
             "u.full_name " +
             "FROM assets a " +
             "LEFT JOIN users u " +
             "ON a.assigned_user_id = u.user_id " +
             "ORDER BY a.asset_code";

     try {
         Connection con = DBConn.MyConnection();

         PreparedStatement pst =
                 con.prepareStatement(sql);

         ResultSet rs =
                 pst.executeQuery();

         while (rs.next()) {

             Object[] row = {
                     rs.getInt("asset_id"),
                     rs.getString("asset_code"),
                     rs.getString("asset_name"),
                     rs.getString("asset_type"),
                     rs.getString("serial_number"),
                     rs.getString("status"),
                     rs.getString("full_name")
             };

             assets.add(row);
         }

         rs.close();
         pst.close();
         con.close();

     } catch (Exception e) {
         e.printStackTrace();
     }

     return assets;
 }


 // ==========================================
 // ASSIGN ASSET TO EMPLOYEE
 // ==========================================

 public boolean assignAsset(
         int assetId,
         int userId) {

     String sql =
             "UPDATE assets " +
             "SET assigned_user_id = ? " +
             "WHERE asset_id = ?";

     try {
         Connection con = DBConn.MyConnection();

         PreparedStatement pst =
                 con.prepareStatement(sql);

         pst.setInt(1, userId);
         pst.setInt(2, assetId);

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