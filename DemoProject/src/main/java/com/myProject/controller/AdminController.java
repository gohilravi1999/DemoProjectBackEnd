package com.myProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProject.dao.ProductRepository;
import com.myProject.dao.RoleRepository;
import com.myProject.dao.UserRepository;

import com.myProject.model.Product;
import com.myProject.model.UserInformation;
import com.myProject.services.UserDetailsServiceImplementation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	RoleRepository roleRepository; 
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserDetailsServiceImplementation userDetailsServiceImp;
	
	@GetMapping("/getListOfActiveUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserInformation>> getAllActiveUser() {
		return userDetailsServiceImp.getAllActiveUser();
	  }
	
	@GetMapping("/getAllUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserInformation>> getAllUser() {
		return userDetailsServiceImp.getAllUser();
	  }
	
	@GetMapping("/getListOfInActiveUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserInformation>> getAllInActiveUser() {
		
		return userDetailsServiceImp.getAllInActiveUser();
	  }
	
	@PutMapping("/makeInactive")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> makeInactive(@RequestBody UserInformation userInformation)
	{
		return userDetailsServiceImp.makeInactive(userInformation);
	}
	
	@PutMapping("/makeActive")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> makeActive(@RequestBody UserInformation userInformation)
	{
		return userDetailsServiceImp.makeActive(userInformation);
	}
	
	 @PostMapping("/addProduct")
	 @PreAuthorize("hasRole('ADMIN')")
	 public ResponseEntity<?> createProduct(@RequestBody Product productInformation) {
		 
		 return userDetailsServiceImp.createProduct(productInformation);
	  }
	 
	 @GetMapping("/getListOfActiveProduct")
		@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
		public ResponseEntity<List<Product>> getAllActiveProduct() {
		 
		 return userDetailsServiceImp.getAllActiveProduct();
		 
		  }
	 
	 	@GetMapping("/getListOfInActiveProduct")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<List<Product>> getAllInActiveProduct() {
		 
		 return userDetailsServiceImp.getAllInActiveProduct();
		 
		 }
	 
	 	@PutMapping("/makeInactiveProduct")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<?> makeInactiveProduct(@RequestBody Product product)
		{
	 		return userDetailsServiceImp.makeInactiveProduct(product);
		}
		
		@PutMapping("/makeActiveProduct")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<?> makeActiveProduct(@RequestBody Product product)
		{
			return userDetailsServiceImp.makeActiveProduct(product);
		}
		
		@PutMapping("/editProduct/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		  public ResponseEntity<?> editProduct(@PathVariable("id") Long id,
				  										@RequestBody Product productInformation) {
			
			 return userDetailsServiceImp.editProduct(id,productInformation);
			
		}
		
		@GetMapping("/getProduct/{id}")
		@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
		public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
			return userDetailsServiceImp.getProductById(id);
		}
}

