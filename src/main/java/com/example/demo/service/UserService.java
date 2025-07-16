package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User registerUser(String username, String password) {
		if (userRepository.findByUsername(username).isPresent()) {
			throw new IllegalArgumentException("Username already exists");
		}

		 User newUser = new User(username, password);
	        return userRepository.save(newUser);
	    }

	public User loginUser(String username, String password) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));

		if (!user.getPassword().equals(password)) {
			throw new IllegalArgumentException("Invalid username or password.");
		}

		return user;
	}
}
