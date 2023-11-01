package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User getUserByEmailAndName(String email,String name);
	
	public User findByUsername(String username);
}
