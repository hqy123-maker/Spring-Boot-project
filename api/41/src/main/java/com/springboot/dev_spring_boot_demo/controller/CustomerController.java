package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/customers")
public class CustomerController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/list-customer")
    public String listCustomers(Model model) {
        TypedQuery<Customer> query = entityManager.createQuery("FROM Customer", Customer.class);
        List<Customer> customers = query.getResultList();
        model.addAttribute("customers", customers);
        return "admin/customers/list-customer";
    }

    @GetMapping("/customer-form-insert")
    public String showInsertForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "admin/customers/customer-form-insert";
    }

    @GetMapping("/customer-form-update")
    public String showUpdateForm(@RequestParam("id") int id, Model model) {
        Customer customer = entityManager.find(Customer.class, id);
        model.addAttribute("customer", customer);
        return "admin/customers/customer-form-update";
    }

    @PostMapping("/save")
    @Transactional
    public String saveCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        try {
            if (customer.getId() == 0) {
                entityManager.persist(customer);
                redirectAttributes.addFlashAttribute("success", "Customer added successfully!");
            } else {
                entityManager.merge(customer);
                redirectAttributes.addFlashAttribute("success", "Customer updated successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/customers/list-customer";
    }

    @GetMapping("/delete")
    @Transactional
    public String deleteCustomer(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        try {
            Customer customer = entityManager.find(Customer.class, id);
            if (customer != null) {
                entityManager.remove(customer);
                redirectAttributes.addFlashAttribute("success", "Customer deleted successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting customer: " + e.getMessage());
        }
        return "redirect:/admin/customers/list-customer";
    }
}