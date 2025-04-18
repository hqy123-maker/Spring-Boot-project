package com.springboot.dev_spring_boot_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller

public class AdminController {
    @GetMapping("/admin")
    public String dashboard() {
        return "dashboard";
    }

}
