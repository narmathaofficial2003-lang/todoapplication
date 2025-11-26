package dev.codeio.HelloWorld.service;

import dev.codeio.HelloWorld.models.Todo;
import dev.codeio.HelloWorld.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class
TodoService {

    @Autowired
    private TodoRepository todoRepository;

    // CREATE TODO
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    // GET TODO BY ID
    public Todo getTodoById(long id) {
        return todoRepository.findById(id).orElse(null);
    }

    // GET ALL TODOS
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    // PAGINATION
    public Page<Todo> getAllTodosPages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return todoRepository.findAll(pageable);
    }

    // UPDATE TODO
    public Todo updateTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    // DELETE TODO BY ID
    public void deleteTodoById(long id) {
        todoRepository.deleteById(id);
    }
}

