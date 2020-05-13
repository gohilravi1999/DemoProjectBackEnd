package com.myProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myProject.dao.UserRepository;
import com.myProject.model.UserInformation;
@Service
public class UserDetailsServiceImplementation implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInformation userInfo = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User does not exists with username: " + username));

		return UserDetailsImplementation.getUserDetails(userInfo);
	}
}
