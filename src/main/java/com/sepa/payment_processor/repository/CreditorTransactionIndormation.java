package com.sepa.payment_processor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sepa.payment_processor.entity.TransactionSummary;

public interface CreditorTransactionIndormation extends JpaRepository<TransactionSummary, Long> {

}
