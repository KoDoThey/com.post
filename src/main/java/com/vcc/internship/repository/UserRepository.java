package com.vcc.internship.repository;

import com.vcc.internship.common.config.Properties;
import com.vcc.internship.model.User;
import com.vcc.internship.platform.ConnectionProviders;
import com.vcc.internship.platform.JdbcConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements AutoCloseable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Connection sqlConnection;

    public UserRepository(Properties prop) {
        sqlConnection = ConnectionProviders.getOrCreate("user", new JdbcConfig(prop));
    }

    public User getUserByUserName(String userName) throws SQLException {
        String query = "SELECT * FROM user WHERE userName = ?";
        PreparedStatement ps = sqlConnection.prepareStatement(query);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        User user = new User();
        if (rs.first()) {
            user.setUserID(rs.getString("userID"));
            user.setUserName(rs.getString("userName"));
            user.setFullName(rs.getString("fullName"));
            user.setAge(rs.getInt("age"));
            user.setDeleteStatus(rs.getInt("deleteStatus"));
            user.setPassword(rs.getString("password"));
        }
        return user;
    }

    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO user VALUES (?, ? , ?, ?, ?, ?)";
        PreparedStatement ps = sqlConnection.prepareStatement(query);
        ps.setString(1, user.getUserID());
        ps.setString(2, user.getUserName());
        ps.setString(3, user.getFullName());
        ps.setInt(4, user.getAge());
        ps.setString(5, user.getPassword());
        ps.setInt(6, 0);
        ps.executeUpdate();
    }

    public void updateUser(User user) throws SQLException {
        String query = "UPDATE user SET fullName = ?, age = ? WHERE userName = ?";
        PreparedStatement ps = sqlConnection.prepareStatement(query);
        ps.setString(1, user.getFullName());
        ps.setInt(2, user.getAge());
        ps.setString(3, user.getUserName());
        ps.executeUpdate();
    }

    public void changePassword(String userName, String passwordNew) throws SQLException {
        String query = "UPDATE user SET password = ? WHERE userName = ?";
        PreparedStatement ps = sqlConnection.prepareStatement(query);
        ps.setString(1, passwordNew);
        ps.setString(2, userName);
        ps.executeUpdate();
    }

    public void deleteUser(User user) throws Exception {
        String query = "UPDATE user SET deleteStatus = ? WHERE userName = ?";
        PreparedStatement ps = sqlConnection.prepareStatement(query);
        ps.setInt(1, 1);
        ps.setString(2, user.getUserName());
        ps.executeUpdate();
    }

    @Override
    public void close() throws Exception {
        if (sqlConnection != null) {
            sqlConnection.close();
        }
    }
}
