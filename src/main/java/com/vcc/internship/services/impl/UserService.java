package com.vcc.internship.services.impl;

import com.vcc.internship.common.config.Properties;
import com.vcc.internship.model.User;
import com.vcc.internship.repository.UserRepository;
import com.vcc.internship.services.Service;

import java.sql.SQLException;
import java.util.UUID;

public class UserService implements Service {
    private UserRepository userRepository;
    private Properties prop;

    public UserService(Properties prop) {
        this.userRepository = new UserRepository(prop);
        this.prop = prop;
    }

    public User getUserByUserName(String userName) throws SQLException {
        User user = userRepository.getUserByUserName(userName);
        if (user.getDeleteStatus() == 1) {
            return null;
        } else return userRepository.getUserByUserName(userName);
    }

    public void addUser(User user) throws SQLException {
        user.setUserID(UUID.randomUUID().toString());
        userRepository.addUser(user);
    }

    public void updateUser(User user) throws SQLException {
        userRepository.updateUser(user);
    }

    public void deleteUser(User user) throws Exception {
        userRepository.deleteUser(user);
    }

    public void changePassword(String userName, String passwordNew) throws SQLException {
        User user = new User();
        userRepository.changePassword(userName, passwordNew);
    }

    @Override
    public void close() throws Exception {
    }
}
