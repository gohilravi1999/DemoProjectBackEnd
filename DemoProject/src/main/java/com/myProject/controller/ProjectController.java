package com.myProject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myProject.dao.UserRepository;
import com.myProject.inputValidation.InputValidation;
import com.myProject.model.UserInformation;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/signup")
	public String signupUser(@RequestBody UserInformation userInformation)
	{	
			boolean checkFistName = InputValidation.isValidName(userInformation.getFirstName());
			boolean checkLastName = InputValidation.isValidName(userInformation.getLastName());
			boolean checkEmail = InputValidation.isValidEmail(userInformation.getEmail());
			boolean checkPassword = InputValidation.isValidPassword(userInformation.getPassword());
			
			if(checkFistName && checkLastName && checkEmail && checkPassword) {
				System.out.println("Signup Successful");
				userRepository.save(userInformation);
				return "SignUp Successful";
			}
			else
			{
				System.out.println("Signup fail");
				return "Signup fail";
			}
	}
}
