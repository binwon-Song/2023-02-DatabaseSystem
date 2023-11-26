package com.DB.Project.Repository;

import com.DB.Project.Entitiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public User findByID(String id) {
        String sql = "SELECT * FROM Users WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, rowNum) -> {
            User user = new User();
            user.setUserID(resultSet.getLong("UserID"));
            user.setName(resultSet.getString("Name"));
            user.setId(resultSet.getString("ID"));
            user.setPasswd(resultSet.getString("Passwd"));
            return user;
        });
    }

    public User saveUser(User user) {
        String sql = "INSERT INTO Users (Name, ID, Passwd) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getId(), user.getPasswd());
        return user;
    }
}
