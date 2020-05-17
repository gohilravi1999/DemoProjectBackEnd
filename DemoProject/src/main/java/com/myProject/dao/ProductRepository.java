package com.myProject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myProject.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query("SELECT p FROM Product p WHERE p.isActiveProduct = 1")
	public List<Product> findActiveProduct();
	
	@Query("SELECT p FROM Product p WHERE p.isActiveProduct = 0")
	public List<Product> findInActiveProduct();
	
	Boolean existsByName(String name);
	

}
