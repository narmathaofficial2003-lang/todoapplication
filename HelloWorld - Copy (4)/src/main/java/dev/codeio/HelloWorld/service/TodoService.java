package dev.codeio.HelloWorld.service;

import dev.codeio.HelloWorld.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper
    private RowMapper<Todo> rowMapper = (rs, rowNum) -> {
        Todo t = new Todo();
        t.setId(rs.getLong("id"));
        t.setTitle(rs.getString("title"));
        t.setIsCompleted(rs.getBoolean("is_completed"));
        return t;
    };

    // CREATE TODO
    public Todo createTodo(Todo todo) {
        String sql = "INSERT INTO todo(title, is_completed) VALUES (?, ?) RETURNING id";
        Long id = jdbcTemplate.queryForObject(sql, Long.class, todo.getTitle(), todo.getIsCompleted());
        todo.setId(id);
        return todo;
    }

    // GET TODO BY ID
    public Todo getTodoById(long id) {
        String sql = "SELECT * FROM todo WHERE id = ?";
        List<Todo> result = jdbcTemplate.query(sql, rowMapper, id);
        return result.isEmpty() ? null : result.get(0);
    }

    // GET ALL TODOS
    public List<Todo> getTodos() {
        String sql = "SELECT * FROM todo ORDER BY id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    //  JDBC Pagination Returns List<Todo>
    public List<Todo> getAllTodosPages(int page, int size) {
        int offset = page * size;
        String sql = "SELECT * FROM todo ORDER BY id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, size, offset);
    }

    // UPDATE TODO
    public Todo updateTodo(Todo todo) {
        String sql = "UPDATE todo SET title = ?, is_completed = ? WHERE id = ?";
        jdbcTemplate.update(sql, todo.getTitle(), todo.getIsCompleted(), todo.getId());
        return todo;
    }

    // DELETE TODO
    public void deleteTodoById(long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


}
