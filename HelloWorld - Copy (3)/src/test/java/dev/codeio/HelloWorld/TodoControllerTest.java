package dev.codeio.HelloWorld;

import dev.codeio.HelloWorld.controller.TodoController;
import dev.codeio.HelloWorld.models.Todo;
import dev.codeio.HelloWorld.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TodoControllerTest {


    private MockMvc mockMvc;

    @Mock
    private TodoService service;

    @InjectMocks
    private TodoController controller;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/api/v1/todo/get"))
                .andExpect(status().isOk())
                .andExpect(content().string("Todo Service Running"));
    }

    @Test
    void testGetTodos() throws Exception {
        when(service.getTodos()).thenReturn(
                List.of(new Todo(1L, "Task1", false))
        );

        mockMvc.perform(get("/api/v1/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Task1"));
    }

    @Test
    void testGetTodoById() throws Exception {
        when(service.getTodoById(1L))
                .thenReturn(new Todo(1L, "Task1", false));

        mockMvc.perform(get("/api/v1/todo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task1"));
    }

    @Test
    void testCreateTodo() throws Exception {
        Todo input = new Todo(null, "New", false);
        Todo saved = new Todo(1L, "New", false);

        when(service.createTodo(any(Todo.class))).thenReturn(saved);

        mockMvc.perform(
                        post("/api/v1/todo/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(input))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateTodo() throws Exception {
        Todo updated = new Todo(1L, "Updated", true);

        when(service.updateTodo(any(Todo.class))).thenReturn(updated);

        mockMvc.perform(
                        put("/api/v1/todo/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(updated))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test
    void testDeleteTodo() throws Exception {
        doNothing().when(service).deleteTodoById(1L);

        mockMvc.perform(delete("/api/v1/todo/1"))
                .andExpect(status().isOk());

        verify(service).deleteTodoById(1L);
    }


}
