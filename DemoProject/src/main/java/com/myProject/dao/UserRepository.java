package com.myProject.dao;


import org.springframework.data.repository.CrudRepository;

import com.myProject.model.UserInformation;

public interface UserRepository extends CrudRepository<UserInformation, Long>
{
	
}
