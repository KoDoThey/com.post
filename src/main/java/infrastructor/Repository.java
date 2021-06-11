package infrastructor;

import controller.API;
import model.Post;
import model.User;

import java.sql.*;

public class Repository {
    /*
    public static User queryUser(String userID) throws SQLException {
        String sqlGetUser = "select userID, fullName, age from user where userID = ?";
        try {
            Connection conn = DataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlGetUser);
            pstmt.setString(1, userID);
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ;
    }

     */



    public static void addUser(User userAPI) {
        String sqlAddUser = "insert into user values (?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlAddUser))
        {
            pstmt.setString(1, userAPI.getUserID());
            pstmt.setString(2, userAPI.getUserName());
            pstmt.setString(3, userAPI.getFullName());
            pstmt.setInt(4, userAPI.getAge());
            pstmt.setString(5, userAPI.getPassword());
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





/*
    public void addUSer (User user) {
        String sqlAddUSer = "insert into user values (?, \"?\", ?, \"?\" )";
        try (Connection con = DataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sqlAddUSer);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                user = new User(); //note
                user.setUserID(rs.getLong("userID"));
                user.setUserName(rs.getString("userName"));
                user.setFullName(rs.getString("fullName"));
                user.setAge(rs.getInt("age"));
                user.setPassword(rs.getString("password"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
 */

}