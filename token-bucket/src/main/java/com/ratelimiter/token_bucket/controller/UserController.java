package com.ratelimiter.token_bucket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;

import com.ratelimiter.token_bucket.Exception.TooManyRequestException;
import com.ratelimiter.token_bucket.model.User;
import com.ratelimiter.token_bucket.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/v1/user")
	public ResponseEntity<String> getUser(@RequestHeader("x-tenant-id") String id,
			HttpServletRequest request) {
		
		try {
			
			if(!Boolean.TRUE.equals(request.getAttribute("tooManyRequests"))) {
				throw new TooManyRequestException("Your API hit limit exceeded");
			}
			User user = userService.getUser(Integer.parseInt(id));
			return ResponseEntity.ok("Hello Secure User: " + user.getName());
		}catch (TooManyRequests tooManyRequests) {
			throw new TooManyRequestException("your api hit limit exceeded");
		}
		
	}
	
	@GetMapping("/v2/user")
	public String getUserNotsecure(@RequestHeader("x-tenant-id") String id) {
		return "Hello Not Secure User:" + id;
	}
}
