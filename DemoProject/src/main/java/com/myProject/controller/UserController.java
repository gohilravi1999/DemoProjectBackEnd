package com.myProject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProject.dao.UserRepository;
import com.myProject.model.UserInformation;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@PutMapping("/users/{id}")
	  public ResponseEntity<UserInformation> updateUserInformation(@PathVariable("id") long id, @RequestBody UserInformation userInformation) {
	    Optional<UserInformation> userData = userRepository.findById(id);

	    if (userData.isPresent()) {
	    	
	    UserInformation user = userData.get();
	    
	    user.setUsername(userInformation.getUsername());
	    user.setEmail(userInformation.getEmail());
	    
	      return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
	    }
	    else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
}
