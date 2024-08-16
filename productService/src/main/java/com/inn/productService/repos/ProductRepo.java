package com.inn.productService.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.productService.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}