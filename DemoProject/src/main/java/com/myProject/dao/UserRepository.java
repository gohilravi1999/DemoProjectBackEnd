package com.myProject.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.myProject.model.UserInformation;

public interface UserRepository extends CrudRepository<UserInformation, Integer> , JpaRepository<UserInformation, Integer>
{
	Optional<UserInformation> findByEmail(String username);
}
