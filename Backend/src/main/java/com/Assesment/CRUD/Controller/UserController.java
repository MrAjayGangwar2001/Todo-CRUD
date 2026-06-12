package com.Assesment.CRUD.Controller;

import com.Assesment.CRUD.Dto.LoginDto;
import com.Assesment.CRUD.Dto.SignupDto;
import com.Assesment.CRUD.Response.UserResponseDto;
import com.Assesment.CRUD.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Signup & Login APIs")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Home page", description = "Public endpoint — no login needed")
    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("You are on Home Page, No Login Needed!");
    }

    @Operation(summary = "Register new user", description = "Password BCrypt hashed, JWT token return hoga")
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@Valid @RequestBody SignupDto userDto) {
        return ResponseEntity.ok(userService.register(userDto));
    }

    @Operation(summary = "Login user", description = "Valid credentials pe JWT token milega")
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody LoginDto userDto) {
        return ResponseEntity.ok(userService.login(userDto));
    }
}