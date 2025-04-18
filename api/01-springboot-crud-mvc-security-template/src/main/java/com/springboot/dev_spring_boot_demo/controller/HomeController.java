package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.Student;
import com.springboot.dev_spring_boot_demo.entity.product;
import com.springboot.dev_spring_boot_demo.service.ProductService;
import com.springboot.dev_spring_boot_demo.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;


    @GetMapping("/about-us")
    public String aboutUs() {
        return "about-us";
    }

    @GetMapping("/services")
    public String services() {
        return "services";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

    @GetMapping("/contact-us")
    public String contactUs() {
        return "contact-us";
    }

    @GetMapping("/menu")
    public String showMenu(Model model) { // Đảm bảo Model được import
        List<product> products = productService.findAll(); // Lấy dữ liệu từ database
        model.addAttribute("products", products); // Truyền vào Thymeleaf
        return "Menu"; // Trả về trang menu.html
    }
    @GetMapping("/ourstory")
    public String ourstory() {return "ourstory";}


}
