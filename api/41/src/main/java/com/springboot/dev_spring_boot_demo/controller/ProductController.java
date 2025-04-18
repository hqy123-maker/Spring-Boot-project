package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.product;
import com.springboot.dev_spring_boot_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list-product")
    public String listProducts(Model model) {
        List<product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/products/list-product";
    }

    @GetMapping("/product-form-insert")
    public String showInsertForm(Model model) {
        model.addAttribute("product", new product());
        return "admin/products/product-form-insert";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") product product,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              Model model) {
        try {
            if (!imageFile.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get("src/main/resources/static/default/images/", fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, imageFile.getBytes());
                product.setImageUrl("/default/images/" + fileName);
            }
            productService.save(product);
            return "redirect:/admin/products/list-product";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload image: " + e.getMessage());
            return imageFile.isEmpty() ? "admin/products/product-form-insert" : "admin/products/product-form-update";
        }
    }

    @GetMapping("/product-form-update")
    public String showUpdateForm(@RequestParam("id") Long id, Model model) {
        product product = productService.findById(id.intValue());
        if (product == null) {
            return "redirect:/admin/products/list-product";
        }
        model.addAttribute("product", product);
        return "admin/products/product-form-update";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") Long id) {
        productService.deleteById(id.intValue());
        return "redirect:/admin/products/list-product";
    }
}