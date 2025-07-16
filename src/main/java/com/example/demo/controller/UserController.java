package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.UserRequest;
import com.example.demo.model.dto.JwtResponse;
import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/react/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRequest request) {
		try {
			User user = userService.registerUser(request.getUsername(), request.getPassword());
			String token = jwtUtil.generateToken(request.getUsername()); 
			return ResponseEntity.ok(new JwtResponse(token));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserRequest request) {
		try {
			User user = userService.loginUser(request.getUsername(), request.getPassword());
			String token = jwtUtil.generateToken(user.getUsername());
			return ResponseEntity.ok(new JwtResponse(token));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}