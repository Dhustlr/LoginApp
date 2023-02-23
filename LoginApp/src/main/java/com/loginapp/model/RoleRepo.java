package com.loginapp.model;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loginapp.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    
	public Role findByName(String name);
}
