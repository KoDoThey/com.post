package com.vcc.internship.services.impl;

import com.vcc.internship.common.config.Properties;
import com.vcc.internship.model.Post;
import com.vcc.internship.repository.PostRepository;
import com.vcc.internship.services.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostService implements Service {
    private PostRepository postRepository;
    private Properties props;

    public PostService(Properties props) {
        this.postRepository = new PostRepository(props);
        this.props = props;
    }

    public Post getPostById(String postID) throws SQLException {
        Post post = postRepository.getPostById(postID);
        if (post.getDeleteStatus() == 1) {
            return null;
        } else {
            return postRepository.getPostById(postID);
        }
    }

//    public Post getPostById(String postID) throws SQLException { //fixme: call 2 times (if & else)
//        if (postRepository.getPostById(postID).getDeleteStatus()) {
//            return null;
//        } else {
//            return postRepository.getPostById(postID);
//        }
//    }

    public List<Post> getPostByUserId(String userID) throws SQLException {
        List<Post> posts = postRepository.getPostByUserId(userID);
        return posts;
    }

    public void addPost(Post post) throws SQLException {
        post.setPostID(UUID.randomUUID().toString());
        post.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        postRepository.addPost(post);
    }

    public void updatePost(Post post) throws SQLException {
        post.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        postRepository.updatePost(post);
    }

    public void deletePost(Post post) throws SQLException {
        post.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        postRepository.deletePost(post);
    }

    @Override
    public void close() throws Exception {
        if (postRepository != null) {
            postRepository.close();
        }
    }
}
