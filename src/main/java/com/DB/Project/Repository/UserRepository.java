package com.DB.Project.Repository;

import com.DB.Project.Entitiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
            user.setUserID(resultSet.getInt("UserID"));
            user.setName(resultSet.getString("Name"));
            user.setId(resultSet.getString("ID"));
            user.setPasswd(resultSet.getString("Passwd"));
            return user;
        });
    }

    @Transactional
    public User saveUser(User user) {
        String sql = "INSERT INTO Users (Name, ID, Passwd) VALUES (?, ?, ?)";
        String userDoc="INSERT INTO Doc(DocID,UserID) VALUES (?, ?)";
        String initQuery = "INSERT INTO ClouserTable (AncestorDocID, DescendantDocID,Depth) VALUES (?,?,0)";

        jdbcTemplate.update(sql, user.getName(), user.getId(), user.getPasswd());
        int userId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        jdbcTemplate.update(userDoc,userId,userId);
        jdbcTemplate.update(initQuery,userId,userId);
        return user;
    }
}
