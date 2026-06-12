package com.Assesment.CRUD.Repository;

import com.Assesment.CRUD.Model.TodoModel;
import com.Assesment.CRUD.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepo extends JpaRepository<TodoModel, Long> {

    // Sirf us user ke todos
    List<TodoModel> findByUser(UserModel user);

    // Delete/Update ke time verify karo ki todo us user ka hi hai
    Optional<TodoModel> findByIdAndUser(Long id, UserModel user);

}
