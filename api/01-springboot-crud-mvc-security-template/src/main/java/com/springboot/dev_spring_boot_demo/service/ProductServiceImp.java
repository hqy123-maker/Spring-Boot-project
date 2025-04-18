package com.springboot.dev_spring_boot_demo.service;

import com.springboot.dev_spring_boot_demo.dao.ProductDAO;
import com.springboot.dev_spring_boot_demo.entity.product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductDAO productDAO;

    @Autowired
    public ProductServiceImp(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public List<product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public product findById(int id) {
        return productDAO.findById(id);
    }

    @Override
    public product save(product product) {
        return productDAO.save(product);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        productDAO.deleteById(id);
    }
}
