package dev.codeio.HelloWorld.repository;

import dev.codeio.HelloWorld.models.Todo;
// import org.springframework.data.jpa.repository.JpaRepository;   // JPA CODE

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
// public interface TodoRepository extends JpaRepository<Todo, Long> {   // JPA CODE
// }   // END JPA CODE
public class TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ===========================
    //      JDBC METHODS
    // ===========================

    public int create(Todo todo) {
        String sql = "INSERT INTO todo (title, is_completed) VALUES (?, ?)";
        return jdbcTemplate.update(sql, todo.getTitle(), todo.getIsCompleted());
    }

    public Todo findById(Long id) {
        String sql = "SELECT * FROM todo WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Todo(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getBoolean("is_completed")
                ),
                id
        );
    }

    public List<Todo> findAll() {
        String sql = "SELECT * FROM todo";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Todo(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getBoolean("is_completed")
                )
        );
    }

    public int update(Todo todo) {
        String sql = "UPDATE todo SET title = ?, is_completed = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                todo.getTitle(),
                todo.getIsCompleted(),
                todo.getId()
        );
    }

    public int delete(Long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
