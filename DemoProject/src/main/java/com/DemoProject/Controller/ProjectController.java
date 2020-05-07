package com.DemoProject.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.DemoProject.Dao.UserRepository;
import com.DemoProject.InputValidation.InputValidation;
import com.DemoProject.Model.UserInformation;

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
