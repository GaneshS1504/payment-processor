package com.ratelimiter.token_bucket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ratelimiter.token_bucket.Exception.UserNotFoundException;
import com.ratelimiter.token_bucket.model.User;
import com.ratelimiter.token_bucket.model.repo.UserRepository;

@Service
public class UserService {
	
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User getUser(int  user) {
		Optional<User> userOp = userRepository.findById(user);
		if(userOp.isPresent()) {
			return userOp.get();
		} else {
			throw new UserNotFoundException("user not found");
		}
	}
	
}
