package com.sepa.payment_processor.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class XmlConfiguration {

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {

		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setPackagesToScan("com.sepa.payment_processor.model");
		
		jaxb2Marshaller.setSchema(new ClassPathResource("schema/xmlsct.xsd"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jaxb.formatted.output", true);
		jaxb2Marshaller.setMarshallerProperties(map);
		
		return jaxb2Marshaller;
	}
}
