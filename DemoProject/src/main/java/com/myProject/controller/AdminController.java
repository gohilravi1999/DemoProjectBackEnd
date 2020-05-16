package com.myProject.controller;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProject.dao.ProductRepository;
import com.myProject.dao.RoleRepository;
import com.myProject.dao.UserRepository;
import com.myProject.jwtResponse.MessageResponse;

import com.myProject.model.Product;
import com.myProject.model.UserInformation;

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
	
	@GetMapping("/getListOfActiveUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserInformation>> getAllActiveUser() {
	    try {
	      List<UserInformation> users = new ArrayList<UserInformation>();

	        userRepository.findActiveUser().forEach(users::add);

	      if (users.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(users, HttpStatus.OK);
	      
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	@GetMapping("/getListOfInActiveUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserInformation>> getAllInActiveUser() {
	    try {
	      List<UserInformation> users = new ArrayList<UserInformation>();

	        userRepository.findInActiveUser().forEach(users::add);

	      if (users.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(users, HttpStatus.OK);
	      
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	@PutMapping("/makeInactive")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> makeInactive(@RequestBody UserInformation userInformation)
	{
		Optional<UserInformation> userData = userRepository.findByUsername(userInformation.getUsername());
	    UserInformation user = userData.get();
	    user.setActive(false);
	    return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
	}
	
	@PutMapping("/makeActive")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> makeActive(@RequestBody UserInformation userInformation)
	{
		Optional<UserInformation> userData = userRepository.findByUsername(userInformation.getUsername());
	    UserInformation user = userData.get();
	    user.setActive(true);
	    return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
	}
	
	 @PostMapping("/addProduct")
	 @PreAuthorize("hasRole('ADMIN')")
	 public ResponseEntity<?> createTutorial(@RequestBody Product productInformation) {
		 if (productRepository.existsByName(productInformation.getName())) 
		 {
			 return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Product is already added!"));
		 }
		 else
		 {
			 productRepository.save(productInformation);
			 return ResponseEntity.ok(new MessageResponse("Product is added successfully!"));
		 } 
	  }
}

