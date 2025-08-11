package com.sepa.payment_processor.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"cstmrCdtTrfInitn"})
@Getter
@Setter
@NoArgsConstructor
public class SctDocument {
	
	private CstmrCdtTrfInitn cstmrCdtTrfInitn;

}
