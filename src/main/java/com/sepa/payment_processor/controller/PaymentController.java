package com.sepa.payment_processor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sepa.payment_processor.entity.TransactionSummary;
import com.sepa.payment_processor.service.XmlFileRead;

@RestController
@RequestMapping("/api/xml-parser")
public class PaymentController {

	private final XmlFileRead xmlFileReadService;

	public PaymentController(XmlFileRead xmlFileReadService) {
		this.xmlFileReadService = xmlFileReadService;
	}

	@PostMapping("/upload")
	public ResponseEntity<?> parseAndSaveXmlFile(@RequestParam("file") MultipartFile multipartFile) {

		try {

			List<TransactionSummary> res = xmlFileReadService.uploadFile(multipartFile);
			return ResponseEntity.ok(res);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Failed to process XML: " + e.getMessage());
		}

	}

}
