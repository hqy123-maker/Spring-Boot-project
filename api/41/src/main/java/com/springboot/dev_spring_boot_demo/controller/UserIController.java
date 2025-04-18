package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserIController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        List<User> users = query.getResultList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        User user = entityManager.find(User.class, username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (entityManager.find(User.class, user.getUsername()) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict for existing username
            }
            entityManager.persist(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{username}")
    @Transactional
    public ResponseEntity<User> updateUser(@PathVariable("username") String username,
                                           @RequestBody User user) {
        try {
            User existingUser = entityManager.find(User.class, username);
            if (existingUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Update fields
            existingUser.setPassword(user.getPassword());
            existingUser.setEnabled(user.isEnabled());

            User updatedUser = entityManager.merge(existingUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{username}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
        try {
            User user = entityManager.find(User.class, username);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            entityManager.remove(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}