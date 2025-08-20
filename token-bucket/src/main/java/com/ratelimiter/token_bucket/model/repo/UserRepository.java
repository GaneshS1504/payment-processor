package com.ratelimiter.token_bucket.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ratelimiter.token_bucket.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
