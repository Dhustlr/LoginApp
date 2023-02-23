package com.loginapp.service;

import java.util.List;

import com.loginapp.dto.UserDto;
import com.loginapp.entity.User;

public interface UserService {
	
    public void saveUser(UserDto userDto);

    public User findByEmail(String email);

    public List<UserDto> findAllUsers();
}
