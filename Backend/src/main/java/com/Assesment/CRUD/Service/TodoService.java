package com.Assesment.CRUD.Service;

import com.Assesment.CRUD.Dto.TodoDto;
import com.Assesment.CRUD.Exception.Custom.TodoNotFoundException;
import com.Assesment.CRUD.Model.TodoModel;
import com.Assesment.CRUD.Model.UserModel;
import com.Assesment.CRUD.Repository.TodoRepo;
import com.Assesment.CRUD.Repository.UserRepo;
import com.Assesment.CRUD.Response.TodoResponse;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepo todoRepo;
    private final UserRepo userRepo;


    private UserModel getLoggedInUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName(); // ← JWT filter mein email set kiya tha

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ValidationException("User not found"));
    }

    // To Create Todos
    public TodoResponse createTodo(TodoDto  todoDto) {
        UserModel user = getLoggedInUser();

        TodoModel todoModel = new TodoModel();
        todoModel.setTitle(todoDto.getTitle());
        todoModel.setDescription(todoDto.getDescription());

        todoModel.setUser(user);

        todoRepo.save(todoModel);

        return new TodoResponse(todoModel.getId(), todoModel.getTitle(), todoModel.getDescription());
    }

    // Get All Todos

    public List<TodoDto> getAllTodo() {
        UserModel user = getLoggedInUser();

        List<TodoModel> todoModels = todoRepo.findByUser(user);
        List<TodoDto> todoDtos = todoModels.stream().map(tm -> new TodoDto(tm.getId(), tm.getTitle(), tm.getDescription())).toList();
        return  todoDtos;

    }

    // To Update Todos
    public TodoResponse updateTodo(Long id, TodoDto  todoDto) {
        UserModel user = getLoggedInUser();

        Optional<TodoModel> todoModel = todoRepo.findByIdAndUser(id, user);
        if (todoModel.isPresent()) {
            TodoModel todomodel = todoModel.get();
            if (todoDto.getTitle() != null) {
                todomodel.setTitle(todoDto.getTitle());
            }

            if (todoDto.getDescription() != null) {
                todomodel.setDescription(todoDto.getDescription());
            }

            todoRepo.save(todomodel);

            return new TodoResponse(todomodel.getId(), todomodel.getTitle(), todomodel.getDescription());
        }else {
            throw new TodoNotFoundException(id);
        }
    }

    // To Delete List Items
    public TodoResponse deleteTodo(Long id) {
        UserModel user = getLoggedInUser();

        Optional<TodoModel> todoModel = todoRepo.findByIdAndUser(id, user);
        if (todoModel.isPresent()) {
            TodoModel todomodel = todoModel.get();
            todoRepo.delete(todomodel);
            return new TodoResponse(todomodel.getId(), todomodel.getTitle(), todomodel.getDescription());
        }else {
            throw new TodoNotFoundException(id);
        }
    }


}
