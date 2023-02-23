package com.loginapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.loginapp.dto.UserDto;
import com.loginapp.entity.Role;
import com.loginapp.entity.User;
import com.loginapp.model.RoleRepo;
import com.loginapp.model.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private RoleRepo roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder) {

		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void saveUser(UserDto userDto) {

		User user = new User();

		user.setName(userDto.getFirstName() + " " + userDto.getLastName());
		user.setEmail(userDto.getEmail());

		// encrypt the password once we integrate spring security
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Role role = roleRepository.findByName("ROLE_ADMIN");
		if (role == null) {

			role = checkRoleExist();
		}

		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {

		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserDto> findAllUsers() {

		List<User> users = userRepository.findAll();
		
		return users.stream().map((user) -> convertEntityToDto(user)).collect(Collectors.toList());
	}

	private UserDto convertEntityToDto(User user) {

		UserDto userDto = new UserDto();
		String[] name = user.getName().split(" ");
		userDto.setFirstName(name[0]);
		userDto.setLastName(name[1]);
		userDto.setEmail(user.getEmail());
		
		return userDto;
	}

	private Role checkRoleExist() {

		Role role = new Role();
		role.setName("ROLE_ADMIN");
		
		return roleRepository.save(role);
	}
}
