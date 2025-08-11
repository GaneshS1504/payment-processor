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
public class Address {
	
	@XmlElement(name = "Addr")
	private String address;
	@XmlElement(name = "StrtName")
	private String streetName;
	@XmlElement(name = "City")
	private String city;
	@XmlElement(name = "State")
	private String state;
}
