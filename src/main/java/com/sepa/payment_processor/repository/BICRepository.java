package com.sepa.payment_processor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sepa.payment_processor.entity.BicDirectory;

@Repository
public interface BICRepository extends JpaRepository<BicDirectory, Integer> {
	
	public Optional<BicDirectory> findByIbanPrefix(String ibanPrefix);
}
