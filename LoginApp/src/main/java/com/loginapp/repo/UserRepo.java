package com.loginapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loginapp.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);
}
