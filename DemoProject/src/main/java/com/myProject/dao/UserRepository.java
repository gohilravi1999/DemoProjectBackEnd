package com.myProject.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.myProject.model.UserInformation;

@Repository
public interface UserRepository extends JpaRepository<UserInformation, Long> {
	
	@Query("SELECT u FROM UserInformation u WHERE u.isActive = 1")
	public List<UserInformation> findActiveUser();
	
	@Query("SELECT u FROM UserInformation u WHERE u.isActive = 0")
	public List<UserInformation> findInActiveUser();
	
	Optional<UserInformation> findByUsername(String username);
	
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}