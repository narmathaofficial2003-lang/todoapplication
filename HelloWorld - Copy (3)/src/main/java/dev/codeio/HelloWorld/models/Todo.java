package dev.codeio.HelloWorld.models;

import io.swagger.v3.oas.annotations.media.Schema;

// JPA CODE ↓↓↓
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.Id;
// ↑↑↑ JPA CODE

import lombok.Data;
import lombok.NoArgsConstructor;

// import org.antlr.v4.runtime.misc.NotNull;  // REMOVE (Not needed)

@Data
@NoArgsConstructor
// @Entity   // JPA CODE
public class Todo {

    // @Id           // JPA CODE
    // @GeneratedValue   // JPA CODE
    private Long id;

    // @NotNull    // INVALID (wrong import)
    // @NotBlank   // INVALID (not imported)
    @Schema(name="title", example="Complete Spring Boot")
    private String title;

    private Boolean isCompleted;

    // Constructor for convenience
    public Todo(Long id, String title, Boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.isCompleted = isCompleted;
    }
}
