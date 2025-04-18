package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.product;
import com.springboot.dev_spring_boot_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductIController {

    private final ProductService productService;

    @Autowired
    public ProductIController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<product>> getAllProducts() {
        List<product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<product> getProductById(@PathVariable("id") Long id) {
        product product = productService.findById(id.intValue());
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<product> createProduct(
            @RequestPart("product") product product,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get("src/main/resources/static/default/images/", fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, imageFile.getBytes());
                product.setImageUrl("/default/images/" + fileName);
            }
            product savedProduct = productService.save(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<product> updateProduct(
            @PathVariable("id") Long id,
            @RequestPart("product") product product,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            product existingProduct = productService.findById(id.intValue());
            if (existingProduct == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Update fields
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setAvailable(product.isAvailable());

            if (imageFile != null && !imageFile.isEmpty()) {
                // Delete old image if exists
                if (existingProduct.getImageUrl() != null) {
                    Path oldImagePath = Paths.get("src/main/resources/static",
                            existingProduct.getImageUrl());
                    Files.deleteIfExists(oldImagePath);
                }

                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get("src/main/resources/static/default/images/", fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, imageFile.getBytes());
                existingProduct.setImageUrl("/default/images/" + fileName);
            }

            product updatedProduct = productService.save(existingProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        try {
            product product = productService.findById(id.intValue());
            if (product == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // Delete image file if exists
            if (product.getImageUrl() != null) {
                Path imagePath = Paths.get("src/main/resources/static", product.getImageUrl());
                Files.deleteIfExists(imagePath);
            }
            productService.deleteById(id.intValue());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}