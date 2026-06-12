package com.Assesment.CRUD.Dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.Assesment.CRUD.Model.UserModel.Role;


@Data
@NoArgsConstructor
public class SignupDto {

    private Long userId;

    @NotBlank
    private String userName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    private Role role;
}

