package com.Assesment.CRUD.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.Assesment.CRUD.Model.UserModel.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String userName;
    private String email;
    private String token;
    private Role role;

    public UserResponseDto(Long userId, String userName, String email) {
    }
}