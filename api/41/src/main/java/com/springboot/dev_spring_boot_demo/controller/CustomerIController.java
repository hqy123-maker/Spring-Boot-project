package com.springboot.dev_spring_boot_demo.controller;

import com.springboot.dev_spring_boot_demo.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Changed from @Controller to @RestController
@RequestMapping("/api/customers")
public class CustomerIController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        TypedQuery<Customer> query = entityManager.createQuery("FROM Customer", Customer.class);
        List<Customer> customers = query.getResultList();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            entityManager.persist(customer);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") int id, @RequestBody Customer customer) {
        try {
            Customer existingCustomer = entityManager.find(Customer.class, id);
            if (existingCustomer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            customer.setId((long) id);
            Customer updatedCustomer = entityManager.merge(customer);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") int id) {
        try {
            Customer customer = entityManager.find(Customer.class, id);
            if (customer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            entityManager.remove(customer);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}