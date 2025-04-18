package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/system/users")
public class UserController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/list-user")
    public String listUsers(Model model) {
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        model.addAttribute("users", query.getResultList());
        return "system/users/list-user";
    }

    @GetMapping("/user-form-insert")
    public String showInsertForm(Model model) {
        model.addAttribute("user", new User());
        return "system/users/user-form-insert";
    }

    @GetMapping("/user-form-update")
    public String showUpdateForm(@RequestParam("username") String username, Model model) {
        User user = entityManager.find(User.class, username);
        model.addAttribute("user", user);
        return "system/users/user-form-update";
    }

    @PostMapping("/save")
    @Transactional
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            if (user.getUsername() != null && !user.getUsername().isEmpty()) {
                // Kiểm tra nếu đây là thao tác update (user đã tồn tại)
                if (entityManager.find(User.class, user.getUsername()) != null) {
                    // Lấy user hiện có từ database
                    User existingUser = entityManager.find(User.class, user.getUsername());
                    // Chỉ cập nhật các trường có thể thay đổi
                    existingUser.setPassword(user.getPassword());
                    existingUser.setEnabled(user.isEnabled());
                    // Không cần gọi merge vì existingUser đã được quản lý bởi persistence context
                } else {
                    // Thêm mới user
                    entityManager.persist(user);
                }
                redirectAttributes.addFlashAttribute("success", "User saved successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/system/users/list-user";
    }

    @GetMapping("/delete")
    @Transactional
    public String deleteUser(@RequestParam("username") String username, RedirectAttributes redirectAttributes) {
        try {
            User user = entityManager.find(User.class, username);
            if (user != null) {
                entityManager.remove(user);
                redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/system/users/list-user";
    }
}