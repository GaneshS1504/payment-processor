package com.sepa.payment_processor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_payments")
public class TransactionSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String amount;
	private String cdtrName;
	private String cdtrAddress;
	private String cdtrIban;
	private String cdtrBic;
	private String settlementDate;
	private String dbtrName;
	private String dbtrIban;
	private String dbtrBic;
	private String type;

}
