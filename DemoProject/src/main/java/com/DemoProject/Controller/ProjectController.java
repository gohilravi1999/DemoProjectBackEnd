package com.DemoProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.DemoProject.Dao.UserRepository;
import com.DemoProject.Model.UserInformation;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/signup")
	public String signupUser(@RequestBody UserInformation userInformation)
	{		
			userRepository.save(userInformation);
			return "SignUp Successfully";
	}
}
