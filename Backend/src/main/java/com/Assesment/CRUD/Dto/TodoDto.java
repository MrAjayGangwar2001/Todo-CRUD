package com.Assesment.CRUD.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoDto {
    private Long id;


    private String title;
    private String description;


}
