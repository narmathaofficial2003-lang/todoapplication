package dev.codeio.HelloWorld.service;

import dev.codeio.HelloWorld.models.Todo;
import dev.codeio.HelloWorld.repository.TodoRepositoryJdbc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepositoryJdbc todoRepositoryJdbc;

    public Todo getTodoById(Long id) {
        return todoRepositoryJdbc.findById(id);
    }

    public List<Todo> getTodos() {
        return todoRepositoryJdbc.findAll();
    }

    public List<Todo> getAllTodosPages(int page, int size) {
        return todoRepositoryJdbc.findPaged(page, size);
    }

    public Todo createTodo(Todo todo) {
        return todoRepositoryJdbc.save(todo);
    }

    public Todo updateTodo(Todo todo) {
        return todoRepositoryJdbc.update(todo);
    }

    public void deleteTodoById(Long id) {
        todoRepositoryJdbc.delete(id);
    }
}
