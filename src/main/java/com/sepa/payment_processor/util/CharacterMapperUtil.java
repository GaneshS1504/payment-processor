package com.sepa.payment_processor.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CharacterMapperUtil {

	private static CharacterMapperUtil instance;
	private final Map<String, String> charMap;

	private CharacterMapperUtil(String filePath) throws StreamReadException, DatabindException, IOException {

		ObjectMapper obj = new ObjectMapper();
		charMap = obj.readValue(new File(filePath), new TypeReference<>() {
		});
	}
	
	public static synchronized CharacterMapperUtil getInstance(String filePath) throws StreamReadException, DatabindException, IOException {
		
		if(instance == null) {
			instance = new CharacterMapperUtil(filePath);
		}
		
		return instance;
	}

	public String replaceInvalidCharacter(String input) {

		if (input == null)
			return null;
		for (Map.Entry<String, String> entry : charMap.entrySet()) {
			input = input.replace(entry.getKey(), entry.getValue());
		}

		return input;
	}
}
