package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.Authority;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/system/authorities")
public class AuthorityController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/list-authorities")
    public String listAuthorities(Model model) {
        List<Authority> authorities = entityManager.createQuery(
                "FROM Authority", Authority.class).getResultList();
        model.addAttribute("authorities", authorities);
        return "system/authorities/list-authorities";
    }

    @GetMapping("/form-insert")
    public String showInsertForm(Model model) {
        List<String> usernames = entityManager.createQuery(
                "SELECT u.username FROM User u", String.class).getResultList();
        model.addAttribute("usernames", usernames);
        model.addAttribute("authority", new Authority());
        return "system/authorities/form-insert";
    }

    @GetMapping("/form-update")
    public String showUpdateForm(@RequestParam String username,
                                 @RequestParam String authority,
                                 Model model) {
        Authority auth = new Authority(username, authority);
        model.addAttribute("authority", auth);
        return "system/authorities/form-update";
    }

    @PostMapping("/save")
    @Transactional
    public String saveAuthority(@RequestParam(required = false) String oldAuthority,
                                @ModelAttribute Authority authority,
                                RedirectAttributes redirectAttributes) {
        try {
            if (oldAuthority != null && !oldAuthority.isEmpty()) {
                entityManager.createQuery(
                                "DELETE FROM Authority a WHERE a.username = :username AND a.authority = :authority")
                        .setParameter("username", authority.getUsername())
                        .setParameter("authority", oldAuthority)
                        .executeUpdate();
            }

            entityManager.persist(authority);
            redirectAttributes.addFlashAttribute("success", "Authority saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/system/authorities/list-authorities"; // Sửa lại đường dẫn
    }

    @GetMapping("/delete")
    @Transactional
    public String deleteAuthority(@RequestParam String username,
                                  @RequestParam String authority,
                                  RedirectAttributes redirectAttributes) {
        try {
            entityManager.createQuery(
                            "DELETE FROM Authority a WHERE a.username = :username AND a.authority = :authority")
                    .setParameter("username", username)
                    .setParameter("authority", authority)
                    .executeUpdate();

            redirectAttributes.addFlashAttribute("success", "Authority deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting authority: " + e.getMessage());
        }
        return "redirect:/system/authorities/list-authorities"; // Sửa lại đường dẫn
    }
}