git init
package dev.codeio.HelloWorld.controller;

import dev.codeio.HelloWorld.service.TodoService;
import dev.codeio.HelloWorld.models.Todo;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.data.domain.Page; // JPA PAGE (Commented)
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {


    @Autowired
    private TodoService todoService;

    @GetMapping("/get")
    public String getTodo() {
        return "Todo";
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo Retrieved Successfully"),
            @ApiResponse(responseCode = "404", description = "Todo Not Found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable long id) {
        return new ResponseEntity<>(todoService.getTodoById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos() {
        return new ResponseEntity<>(todoService.getTodos(), HttpStatus.OK);
    }

// ---------- JPA PAGE COMMENTED ----------
/*
@GetMapping("/page")
public ResponseEntity<Page<Todo>> getTodosPaged(@RequestParam int page, @RequestParam int size) {
    return new ResponseEntity<>(todoService.getAllTodosPages(page, size), HttpStatus.OK);
}
*/

    // ---------- JDBC TEMPLATE PAGINATION ----------
    @GetMapping("/page")
    public ResponseEntity<List<Todo>> getTodosPagedJdbc(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(todoService.getAllTodosPages(page, size), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        return new ResponseEntity<>(todoService.createTodo(todo), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Todo> updateTodoById(@RequestBody Todo todo) {
        return new ResponseEntity<>(todoService.updateTodo(todo), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable long id) {
        todoService.deleteTodoById(id);
    }


}
