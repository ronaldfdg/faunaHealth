package com.faunahealth.web.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.User;
import com.faunahealth.web.repository.UserRepository;
import com.faunahealth.web.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repositoryUser;
	
	@Override
	public List<User> findAll() {
		return repositoryUser.findUsers();
	}

	@Override
	public void save(User user) {
		repositoryUser.save(user);
	}

	@Override
	public User findById(int id) {
		 Optional<User> optional = repositoryUser.findById(id);
		 
		 if(optional.isPresent())
			 return optional.get();
		 
		 return null;
	}

	@Override
	public User findByUsername(String username) {
		return repositoryUser.findByUsername(username);
	}

}
