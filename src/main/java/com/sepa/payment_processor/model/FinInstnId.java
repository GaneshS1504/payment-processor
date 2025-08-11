package com.sepa.payment_processor.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlAccessorType(XmlAccessType
		.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class FinInstnId {
	
	@XmlElement(name = "BIC")
	private String bic;
	
}
