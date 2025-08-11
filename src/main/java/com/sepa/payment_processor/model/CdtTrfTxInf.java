package com.sepa.payment_processor.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class CdtTrfTxInf {
	
	
	@XmlElement(name = "Amt")
	private Amt amt;
	@XmlElement(name = "Cdtr")
	private Party cdtr;
	@XmlElement(name = "CdtrAcct")
	private Account cdtrAcct;
	@XmlElement(name = "CdtrAgt")
	private Agent cdtrAgt;
	@XmlElement(name = "RmtInf")
	private RmtInf rmtInf;

}
