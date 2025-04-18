package com.springboot.dev_spring_boot_demo.entity;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "authorities")

public class Authority {
    
    @Id
    private Long id;

    public static List<String> getAllRoles() {
        return Arrays.asList("ROLE_USER", "ROLE_ADMIN", "ROLE_MANAGER");
    }


    @Column(name = "username")
    private String username;


    @Column(name = "authority")
    private String authority;

    // Constructors
    public Authority() {}

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


}