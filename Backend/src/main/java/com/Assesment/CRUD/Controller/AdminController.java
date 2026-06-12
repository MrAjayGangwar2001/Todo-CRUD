package com.Assesment.CRUD.Controller;

import com.Assesment.CRUD.Model.UserModel;
import com.Assesment.CRUD.Repository.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin", description = "Admin only APIs — ADMIN role required")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminController {

    private final UserRepo userRepo;

    @Operation(summary = "Get all users", description = "Sirf ADMIN dekh sakta hai — ADMIN role required")
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }
}