package com.sepa.payment_processor.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.xml.sax.SAXParseException;

@ControllerAdvice
public class UnmarshallExceptionHandler {
	
    private static final Logger log = LoggerFactory.getLogger(UnmarshallExceptionHandler.class);

	
	@ExceptionHandler(exception = UnmarshallingFailureException.class)
	public ResponseEntity<Map<String,Object>> handleUnmarshallException(UnmarshallingFailureException ex){
		
        log.error("XML Unmarshalling failed", ex);
		
		Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Invalid XML format");
        errorResponse.put("message", ex.getMostSpecificCause().getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(exception = SAXParseException.class)
	public ResponseEntity<Map<String,Object>> handleUnmarshallException(SAXParseException ex){
		
        log.error("XML Unmarshalling failed", ex);
		
		Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Invalid XML format");
        errorResponse.put("message", ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		
	}
	
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
	    
		 log.error("Unexpected error", ex);

	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("timestamp", LocalDateTime.now());
	        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
	        errorResponse.put("error", "Internal Server Error");
	        errorResponse.put("message", ex.getMessage());

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
}
