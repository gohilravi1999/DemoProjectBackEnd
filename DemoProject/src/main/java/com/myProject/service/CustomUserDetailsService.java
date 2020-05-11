package com.myProject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myProject.dao.UserRepository;
import com.myProject.model.CustomUserDetails;
import com.myProject.model.UserInformation;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInformation> optionalUserInformation=userRepository.findByEmail(username);
		
		optionalUserInformation
			.orElseThrow(() -> new UsernameNotFoundException("Email not found"));
		
		return optionalUserInformation
			.map(CustomUserDetails::new).get();
	}

}
