package com.vcc.internship.services.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vcc.internship.common.config.Properties;
import com.vcc.internship.model.Post;
import com.vcc.internship.repository.PostRepository;
import com.vcc.internship.services.Service;

import java.sql.SQLException;
import java.util.UUID;

public class PostService implements Service {
    private PostRepository postRepository;
    private Properties props;

    public PostService(Properties props) {
        this.postRepository = new PostRepository(props);
        this.props = props;
    }

    public Post getPost(String title) {
        return null;
    }

    public void addPost(Post post) throws SQLException {
        post.setPostID(UUID.randomUUID().toString());
        postRepository.addPost(post);
    }



    @Override
    public void close() throws Exception {
        if (postRepository != null) {
            postRepository.close();
        }
    }
}
