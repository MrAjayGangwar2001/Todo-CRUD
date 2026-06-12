package com.Assesment.CRUD.Controller;

import com.Assesment.CRUD.Dto.TodoDto;
import com.Assesment.CRUD.Response.TodoResponse;
import com.Assesment.CRUD.Service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
@Tag(name = "Todo", description = "CRUD APIs — JWT required")
@SecurityRequirement(name = "Bearer Authentication")
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "Create todo", description = "Naya todo banao — JWT required")
    @PostMapping("/create")
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoDto todoDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(todoService.createTodo(todoDto));
    }

    @Operation(summary = "Get all todos", description = "Saare todos fetch karo — JWT required")
    @GetMapping("/allTodo")
    public ResponseEntity<List<TodoDto>> getAllTodo() {
        return ResponseEntity.ok(todoService.getAllTodo());
    }

    @Operation(summary = "Update todo", description = "Todo update karo by ID — JWT required")
    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id,
                                                   @RequestBody TodoDto todoDto) {
        return ResponseEntity.ok(todoService.updateTodo(id, todoDto));
    }

    @Operation(summary = "Delete todo", description = "Todo delete karo by ID — JWT required")
    @DeleteMapping("/{id}")
    public ResponseEntity<TodoResponse> deleteTodo(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.deleteTodo(id));
    }
}