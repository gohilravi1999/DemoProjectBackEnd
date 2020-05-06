package com.DemoProject.Dao;

import org.springframework.data.repository.CrudRepository;

import com.DemoProject.Model.UserInformation;

public interface UserRepository extends CrudRepository<UserInformation, Long> {

}
