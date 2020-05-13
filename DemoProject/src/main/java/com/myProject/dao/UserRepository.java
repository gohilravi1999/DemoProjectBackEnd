package com.myProject.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.myProject.model.UserInformation;

@Repository
public interface UserRepository extends JpaRepository<UserInformation, Long> {
	
	Optional<UserInformation> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}