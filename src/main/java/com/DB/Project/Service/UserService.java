package com.DB.Project.Service;

import com.DB.Project.Entitiy.User;
import com.DB.Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(UserRepository userRepository, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserByID(String id) {
        System.out.println(id);
        return userRepository.findByID(id);
    }

    public User createUser(User user) {
        return userRepository.saveUser(user);
    }

    public boolean validateUser(String id, String passwd) {
        User user = userRepository.findByID(id);
        return user != null && passwd.equals(user.getPasswd());
    }
}