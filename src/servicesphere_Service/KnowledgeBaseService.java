package servicesphere_Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import JarJdbc.DBConn;

public class KnowledgeBaseService {

    public List<Object[]> getAllArticles() {

        List<Object[]> articles =
                new ArrayList<>();

        String sql =
                "SELECT article_id, title, category, content " +
                "FROM knowledge_base " +
                "ORDER BY category, title";

        try {

            Connection con =
                    DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Object[] row = {
                    rs.getInt("article_id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("content")
                };

                articles.add(row);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return articles;
    }


    public List<Object[]> searchArticles(
            String keyword) {

        List<Object[]> articles =
                new ArrayList<>();

        String sql =
                "SELECT article_id, title, category, content " +
                "FROM knowledge_base " +
                "WHERE title LIKE ? " +
                "OR category LIKE ? " +
                "OR content LIKE ? " +
                "ORDER BY category, title";

        try {

            Connection con =
                    DBConn.MyConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            String searchText =
                    "%" + keyword + "%";

            pst.setString(1, searchText);
            pst.setString(2, searchText);
            pst.setString(3, searchText);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Object[] row = {
                    rs.getInt("article_id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("content")
                };

                articles.add(row);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return articles;
    }
}