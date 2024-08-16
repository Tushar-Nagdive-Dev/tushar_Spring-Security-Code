package com.inn.productService.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.productService.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

}
