package com.pool.curso.springboot.app.springbootcrud.services;

import java.util.List;

import com.pool.curso.springboot.app.springbootcrud.entities.User;

public interface UserService {

    List <User> finAll();

    User save(User user);

    boolean existsByUsername(String username);
}
