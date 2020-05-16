package com.myProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myProject.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	Boolean existsByName(String name);

}
