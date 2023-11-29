package com.DB.Project.Repository;

import com.DB.Project.Entitiy.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Todo> getTodoByDocId(int docId) {
        String query = "SELECT * FROM TODOList WHERE DocID = ?";
        return jdbcTemplate.query(query, new Object[]{docId}, this::mapRowToTodo);
    }


    private Todo mapRowToTodo(ResultSet rs, int rowNum) throws SQLException {
        Todo todo = new Todo(
                rs.getInt("TodoID"),
                rs.getString("header"),
                rs.getInt("Prior"),
                rs.getInt("DocID")
        );
        return todo;
    }
}
