package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.Authority;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authorities")
public class AuthorityIController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity<List<Authority>> getAllAuthorities() {
        List<Authority> authorities = entityManager.createQuery(
                "FROM Authority", Authority.class).getResultList();
        return new ResponseEntity<>(authorities, HttpStatus.OK);
    }

    @GetMapping("/{username}/{authority}")
    public ResponseEntity<Authority> getAuthority(
            @PathVariable("username") String username,
            @PathVariable("authority") String authority) {
        Authority auth = entityManager.find(Authority.class,
                new Authority(username, authority)); // Note: This assumes proper equals() implementation
        if (auth == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(auth, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getAvailableRoles() {
        return new ResponseEntity<>(Authority.getAllRoles(), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Authority> createAuthority(@RequestBody Authority authority) {
        try {
            if (authority.getUsername() == null || authority.getAuthority() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Check if authority already exists
            Authority existing = entityManager.find(Authority.class, authority);
            if (existing != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            entityManager.persist(authority);
            return new ResponseEntity<>(authority, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{username}/{oldAuthority}")
    @Transactional
    public ResponseEntity<Authority> updateAuthority(
            @PathVariable("username") String username,
            @PathVariable("oldAuthority") String oldAuthority,
            @RequestBody Authority newAuthority) {
        try {
            // Find existing authority
            Authority existing = entityManager.find(Authority.class,
                    new Authority(username, oldAuthority));
            if (existing == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Delete old authority
            entityManager.createQuery(
                            "DELETE FROM Authority a WHERE a.username = :username AND a.authority = :authority")
                    .setParameter("username", username)
                    .setParameter("authority", oldAuthority)
                    .executeUpdate();

            // Save new authority
            entityManager.persist(newAuthority);
            return new ResponseEntity<>(newAuthority, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{username}/{authority}")
    @Transactional
    public ResponseEntity<Void> deleteAuthority(
            @PathVariable("username") String username,
            @PathVariable("authority") String authority) {
        try {
            int deleted = entityManager.createQuery(
                            "DELETE FROM Authority a WHERE a.username = :username AND a.authority = :authority")
                    .setParameter("username", username)
                    .setParameter("authority", authority)
                    .executeUpdate();

            if (deleted == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}