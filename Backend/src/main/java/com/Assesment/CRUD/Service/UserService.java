package com.Assesment.CRUD.Service;

import com.Assesment.CRUD.Dto.LoginDto;
import com.Assesment.CRUD.Dto.SignupDto;
import com.Assesment.CRUD.Model.UserModel;
import com.Assesment.CRUD.Model.UserModel.Role;
import com.Assesment.CRUD.Repository.UserRepo;
import com.Assesment.CRUD.Response.UserResponseDto;
import com.Assesment.CRUD.Security.JwtUtil;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponseDto register(SignupDto user) {

        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new ValidationException("Email already exists");
        }

        // Role set karna — agar nahi aaya toh USER default
        Role role = (user.getRole() != null) ? user.getRole() : Role.USER;

        UserModel newUser = new UserModel();
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(role); // ← ROLE SAVE

        userRepo.save(newUser);

        String token = jwtUtil.generateToken(newUser.getEmail(), role.name());
        return new UserResponseDto(
                newUser.getUserId(),
                newUser.getUserName(),
                newUser.getEmail(),
                token,
                role
        );
    }

    public UserResponseDto login(LoginDto userDto) {

        Optional<UserModel> userModel = userRepo.findByEmail(userDto.getEmail());
        if (userModel.isEmpty()) {
            throw new ValidationException("User Not Registered with us!");
        }

        UserModel user = userModel.get();

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new ValidationException("Wrong Password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new UserResponseDto(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                token,
                user.getRole()
        );
    }
}