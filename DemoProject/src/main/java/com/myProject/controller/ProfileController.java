package com.myProject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProject.dao.UserRepository;
import com.myProject.jwtResponse.MessageResponse;
import com.myProject.model.UserInformation;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/users")
public class ProfileController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;

	@PutMapping("/editProfile/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	  public ResponseEntity<?> updateUserInformation(@PathVariable("id") Long id,
			  										@RequestBody UserInformation userInformation) {
		
		Optional<UserInformation> userDetail = this.userRepository.findById(id);
		UserInformation user = userDetail.get();

		if (userDetail.isPresent()) {
			user.setUsername(userInformation.getUsername());
			user.setEmail(userInformation.getEmail());
			userRepository.save(user);
			return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
		} else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: User is not updated!"));
		}
	}
	
	@PutMapping("/changePassword/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> changePassword(@PathVariable("id") Long id,
											@RequestBody UserInformation userInformation){
		
		Optional<UserInformation> userData = userRepository.findById(id);
		
		if (userData.isPresent()) {
	    	
	    UserInformation user = userData.get();
	    
	    user.setPassword(encoder.encode(userInformation.getPassword()));
	    
	      return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
	    }
	    else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
}
