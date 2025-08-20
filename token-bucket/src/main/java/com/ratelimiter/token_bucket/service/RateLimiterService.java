package com.ratelimiter.token_bucket.service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

@Service
public class RateLimiterService {

	private UserService userService;
	Map<Integer, Bucket> cache = new ConcurrentHashMap<Integer, Bucket>();
	
	public RateLimiterService(UserService userService) {
		super();
		this.userService = userService;
	}

	public Bucket resolveBucket(Integer id) {
		return cache.computeIfAbsent(id, this::getConfigSupplierForUser);
	}

	private Bucket getConfigSupplierForUser(Integer id) {
		
		int limit = userService.getUser(id).getToken();
		
		Bandwidth badwidth = Bandwidth.builder().capacity(limit)
				.refillIntervally(limit, Duration.ofSeconds(30)).build();

		return Bucket.builder().addLimit(badwidth).build();

	}
}
