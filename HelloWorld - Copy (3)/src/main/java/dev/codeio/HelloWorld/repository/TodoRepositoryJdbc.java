package dev.codeio.HelloWorld.repository;

import dev.codeio.HelloWorld.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TodoRepositoryJdbc {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Todo> todoRowMapper = (rs, rowNum) -> {
        Todo todo = new Todo();
        todo.setId(rs.getLong("id"));
        todo.setTitle(rs.getString("title"));
        todo.setCompleted(rs.getBoolean("completed"));
        return todo;
    };

    // --------------------------
    // FIND BY ID (SAFE)
    // --------------------------
    public Todo findById(Long id) {
        String sql = "SELECT * FROM todo WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, todoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null; // return null if not found
        }
    }

    // --------------------------
    // FIND ALL
    // --------------------------
    public List<Todo> findAll() {
        String sql = "SELECT * FROM todo";
        return jdbcTemplate.query(sql, todoRowMapper);
    }

    // --------------------------
    // PAGINATION
    // --------------------------
    public List<Todo> findPaged(int page, int size) {
        int offset = page * size;
        String sql = "SELECT * FROM todo LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, todoRowMapper, size, offset);
    }

    // --------------------------
    // CREATE (RETURN GENERATED ID)
    // --------------------------
    public Todo save(Todo todo) {
        String sql = "INSERT INTO todo (title, completed) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, todo.getTitle());
            ps.setBoolean(2, todo.getCompleted());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            todo.setId(keyHolder.getKey().longValue());
        }

        return todo;
    }

    // --------------------------
    // UPDATE
    // --------------------------
    public Todo update(Todo todo) {
        String sql = "UPDATE todo SET title = ?, completed = ? WHERE id = ?";
        jdbcTemplate.update(sql, todo.getTitle(), todo.getCompleted(), todo.getId());
        return todo;
    }

    // --------------------------
    // DELETE
    // --------------------------
    public void delete(Long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
