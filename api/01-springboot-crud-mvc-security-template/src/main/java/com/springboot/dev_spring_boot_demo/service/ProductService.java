package com.springboot.dev_spring_boot_demo.service;


import com.springboot.dev_spring_boot_demo.entity.product;

import java.util.List;

public interface ProductService {
    List<product> findAll();
    product findById(int id);
    product save(product product);
    void deleteById(int id);
}
