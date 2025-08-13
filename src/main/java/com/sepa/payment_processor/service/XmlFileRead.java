package com.sepa.payment_processor.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.sepa.payment_processor.entity.BicDirectory;
import com.sepa.payment_processor.entity.TransactionSummary;
import com.sepa.payment_processor.model.Address;
import com.sepa.payment_processor.model.CdtTrfTxInf;
import com.sepa.payment_processor.model.SctDocument;
import com.sepa.payment_processor.repository.BICRepository;
import com.sepa.payment_processor.repository.CreditorTransactionIndormation;
import com.sepa.payment_processor.util.CharacterMapperUtil;
import com.sepa.payment_processor.util.XmlToEmtityMapper;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;

@Service
public class XmlFileRead {

	private final Jaxb2Marshaller jaxb2Marshaller;
	private final CreditorTransactionIndormation creditorTransactionIndormation;
	private CharacterMapperUtil mapper;
	private final String FILE_PATH;
	private final XmlToEmtityMapper xmlToEmtityMapper;
	private final BICRepository bicRepository;

	@PostConstruct
	public void init() throws StreamReadException, DatabindException, IOException {
		this.mapper = CharacterMapperUtil.getInstance(FILE_PATH);
	}

	public XmlFileRead(Jaxb2Marshaller jaxb2Marshaller, CreditorTransactionIndormation creditorTransactionIndormation,
			@Value("${characters.file.path}") String filePath, XmlToEmtityMapper xmlToEmtityMapper,
			BICRepository bicRepository) {
		this.jaxb2Marshaller = jaxb2Marshaller;
		this.creditorTransactionIndormation = creditorTransactionIndormation;
		this.FILE_PATH = filePath;
		this.xmlToEmtityMapper = xmlToEmtityMapper;
		this.bicRepository = bicRepository;
	}

	public List<TransactionSummary> uploadFile(MultipartFile file) {

		File dir = new File("uploads");
		if (!dir.exists())
			dir.mkdir();

		String timestamp = LocalDateTime.now().toString().replace(":", "-").replace(".", "-");
		File savedFile = new File(dir, file.getOriginalFilename() + "_" + timestamp);
		List<TransactionSummary> savedTransactions = null;
		try (FileOutputStream fos = new FileOutputStream(savedFile)) {

			fos.write(file.getBytes());
			savedTransactions = parseAndSave(savedFile);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return savedTransactions;
	}

	private List<TransactionSummary> parseAndSave(File savedFile) throws FileNotFoundException, IOException {

		try (FileInputStream fis = new FileInputStream(savedFile)) {

			// Unmarshal using JAXB
			SctDocument sctDocument = (SctDocument) jaxb2Marshaller.unmarshal(new StreamSource(fis));

			String dbtrName = sctDocument.getCstmrCdtTrfInitn().getPmtInf().getDbtr().getNm();
			String dbtrIban = sctDocument.getCstmrCdtTrfInitn().getPmtInf().getDbtrAcct().getId().getIban();
			String dbtrBic = sctDocument.getCstmrCdtTrfInitn().getPmtInf().getDbtrAgt().getFinInstnId().getBic();
			String type = sctDocument.getCstmrCdtTrfInitn().getPmtInf().getPmtMtd();
			String settlementDate = sctDocument.getCstmrCdtTrfInitn().getPmtInf().getReqdExctnDt();

			List<CdtTrfTxInf> creditorInfo = sctDocument.getCstmrCdtTrfInitn().getPmtInf().getCdtTrfTxInf();
			replaceInvalidCharacters(creditorInfo);
			enrichMissingBic(creditorInfo);

			List<TransactionSummary> txnData = creditorInfo.stream()
					.map(cdtr -> xmlToEmtityMapper.mapToEntity(cdtr, dbtrName, dbtrIban, dbtrBic, type, settlementDate))
					.collect(Collectors.toList());

			return creditorTransactionIndormation.saveAll(txnData);

		} 

	}

	private void replaceInvalidCharacters(List<CdtTrfTxInf> creditsInfoList)
			throws StreamReadException, DatabindException, IOException {

		for (CdtTrfTxInf individualInfo : creditsInfoList) {

			Address address = individualInfo.getCdtr().getAddress();

			// Addr
			if (address.getAddress() != null) {
				String cleaned = mapper.replaceInvalidCharacter(address.getAddress());
				validateField("Addr", cleaned); // Validation step
				address.setAddress(cleaned);
			}

			// StrtName
			if (address.getStreetName() != null) {
				String cleaned = mapper.replaceInvalidCharacter(address.getStreetName());
				validateField("StrtName", cleaned);
				address.setStreetName(cleaned);
			}

			// City
			if (address.getCity() != null) {
				String cleaned = mapper.replaceInvalidCharacter(address.getCity());
				validateField("City", cleaned);
				address.setCity(cleaned);
			}

			// State
			if (address.getState() != null) {
				String cleaned = mapper.replaceInvalidCharacter(address.getState());
				validateField("State", cleaned);
				address.setState(cleaned);
			}
		}

	}

	private void validateField(String fieldName, String value) {

		// Example: Length and character check
		if (value.length() > 70) {
			throw new IllegalArgumentException("Field " + fieldName + " exceeds max length of 70");
		}

		// Allow only A-Z, a-z, 0-9, space and limited punctuation (e.g., / - ? : ( ) .
		// , ')
		if (!value.matches("[A-Za-z0-9\\-?():.,'/ ]*")) {
			throw new IllegalArgumentException("Invalid characters in field: " + fieldName);
		}

	}

	public void enrichMissingBic(List<CdtTrfTxInf> creditsInfoList) {

		for (CdtTrfTxInf txn : creditsInfoList) {

			String iban = txn.getCdtrAcct().getId().getIban();
			String currentBic = txn.getCdtrAgt().getFinInstnId().getBic();

			if (StringUtils.isBlank(currentBic)) {

				String ibanPref = iban.substring(0, 4);
				String enrichedBic = bicRepository.findByIbanPrefix(ibanPref).map(BicDirectory::getBic)
						.orElse("DEFAULTBIC");

				txn.getCdtrAgt().getFinInstnId().setBic(enrichedBic);
			}
		}
	}

}
