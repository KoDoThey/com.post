package com.vcc.internship.repository;

import com.vcc.internship.common.config.Properties;
import com.vcc.internship.model.Post;
import com.vcc.internship.platform.ConnectionProvider;
import com.vcc.internship.platform.ConnectionProviders;
import com.vcc.internship.platform.JdbcConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRepository implements AutoCloseable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Connection sqlConnection;

    public PostRepository(Properties prop) {
        sqlConnection = ConnectionProviders.getOrCreate("post", new JdbcConfig(prop));
    }

    public Post getPostById(String postID) throws SQLException {
        String query = "SELECT * FROM post WHERE postID = ?";
        PreparedStatement ps = sqlConnection.prepareStatement(query);
        ps.setString(1, postID);
        MyResultSet rs = new MyResultSet(ps.executeQuery());
        Post post = new Post();
        if (rs.first()) {
//            post.setPostID(rs.getString("postID"));
            post.setTitle(rs.getString("title"));
            post.setDescription(rs.getString("description"));
            post.setUserID(rs.getString("userID"));
            post.setCreateTime(rs.getTimestamp("createTime"));
            post.setUpdateTime(rs.getTimestamp("updateTime"));
            post.setDeleteStatus(rs.getBoolean("deleteStatus"));
        }
        return post;
    }

    public void addPost(Post post) throws SQLException {
        String query = "INSERT INTO post VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = sqlConnection.prepareStatement(query);
        ps.setString(1, post.getPostID());
        ps.setString(2, post.getTitle());
        ps.setString(3, post.getDescription());
        ps.setString(4, post.getUserID());
        ps.setTimestamp(5, post.getCreateTime());
        ps.setTimestamp(6, post.getUpdateTime());
        ps.setBoolean(7, false);
        ps.execute();
    }

    public void updatePost(Post post) throws SQLException {
        String query = "UPDATE post SET title = ?, description = ?, updateTime = ? WHERE postID = ?";
        PreparedStatement ps = sqlConnection.prepareStatement(query);
        ps.setString(1, post.getTitle());
        ps.setString(2, post.getDescription());
        ps.setTimestamp(3, post.getUpdateTime());
        ps.setString(4, post.getPostID());
        ps.execute();
    }

    public void deletePost(Post post) throws SQLException {
        String query = "UPDATE post SET updateTime = ?, deleteStatus = ? WHERE postID = ?";
        PreparedStatement ps = sqlConnection.prepareStatement(query);
        ps.setTimestamp(1, post.getUpdateTime());
        ps.setBoolean(2, true);
        ps.setString(3, post.getPostID());
        ps.execute();
    }

    @Override
    public void close() throws Exception {
        if (sqlConnection != null) sqlConnection.close();
    }
}
















