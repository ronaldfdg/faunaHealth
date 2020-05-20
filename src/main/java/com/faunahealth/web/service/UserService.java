package com.faunahealth.web.service;

import java.util.List;

import com.faunahealth.web.entity.User;

public interface UserService {
	
	List<User> findAll();
	User findByUsername(String username);
	User findById(int id);
	void save(User user);

}
