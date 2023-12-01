package com.DB.Project.Service;

import com.DB.Project.Entitiy.Todo;
import com.DB.Project.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodoByDocId(int docId) {
        return todoRepository.getTodoByDocId(docId);
    }
    public List<Todo> getTodoByUserId(int userId) {
        return todoRepository.getTodoByUserId(userId);
    }
}
