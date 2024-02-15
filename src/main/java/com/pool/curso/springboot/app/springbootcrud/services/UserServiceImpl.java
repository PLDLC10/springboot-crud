package com.pool.curso.springboot.app.springbootcrud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pool.curso.springboot.app.springbootcrud.entities.Role;
import com.pool.curso.springboot.app.springbootcrud.entities.User;
import com.pool.curso.springboot.app.springbootcrud.repositories.RoleRepository;
import com.pool.curso.springboot.app.springbootcrud.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<User> finAll() {
        return (List<User>) repository.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {

        Optional<Role> optionalRol=roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        optionalRol.ifPresent(roles::add);
        if (user.isAdmin()) {
            Optional<Role> optionalRolAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRolAdmin.ifPresent(roles::add);
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

}
