package com.springboot.dev_spring_boot_demo.dao;

import com.springboot.dev_spring_boot_demo.entity.product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAOImp implements ProductDAO {
    private final EntityManager em;

    @Autowired
    public ProductDAOImp(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<product> findAll() {
        TypedQuery<product> query = em.createQuery("from product", product.class);
        return query.getResultList();
    }

    @Override
    public product findById(int id) {
        return em.find(product.class, id);
    }

    @Override
    @Transactional
    public product save(product product) {
        return em.merge(product);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        product product = em.find(product.class, id);
        em.remove(product);
    }
}
