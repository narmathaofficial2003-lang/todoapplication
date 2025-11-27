package dev.codeio.HelloWorld;

import dev.codeio.HelloWorld.models.Todo;
import dev.codeio.HelloWorld.repository.TodoRepositoryJdbc;
import dev.codeio.HelloWorld.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Mock
    private TodoRepositoryJdbc repo;

    @InjectMocks
    private TodoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTodo() {
        Todo input = new Todo(null, "Task1", false);
        Todo saved = new Todo(1L, "Task1", false);

        when(repo.save(input)).thenReturn(saved);

        Todo result = service.createTodo(input);

        assertEquals(1L, result.getId());
        assertEquals("Task1", result.getTitle());
        verify(repo, times(1)).save(input);
    }

    @Test
    void testGetTodoById() {
        Todo t = new Todo(1L, "Task1", false);

        when(repo.findById(1L)).thenReturn(t);

        Todo result = service.getTodoById(1L);

        assertNotNull(result);
        assertEquals("Task1", result.getTitle());
        verify(repo).findById(1L);
    }

    @Test
    void testGetTodos() {
        when(repo.findAll()).thenReturn(
                List.of(
                        new Todo(1L, "Task1", false),
                        new Todo(2L, "Task2", true)
                )
        );

        List<Todo> list = service.getTodos();

        assertEquals(2, list.size());
        verify(repo).findAll();
    }

    @Test
    void testUpdateTodo() {
        Todo updated = new Todo(1L, "Updated", true);

        when(repo.update(updated)).thenReturn(updated);

        Todo result = service.updateTodo(updated);

        assertEquals("Updated", result.getTitle());
        assertTrue(result.getCompleted());
        verify(repo).update(updated);
    }

    @Test
    void testDeleteTodo() {
        doNothing().when(repo).delete(1L);

        service.deleteTodoById(1L);

        verify(repo).delete(1L);
    }
}
