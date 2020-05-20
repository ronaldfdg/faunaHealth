package com.faunahealth.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faunahealth.web.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u inner join fetch u.role "
			+ "order by u.name asc")
	List<User> findUsers();
	
	User findByUsername(String username);
	
}
