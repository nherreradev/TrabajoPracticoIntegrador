package com.unlam.tpi.infraestructura.manejoExcepciones;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.unlam.tpi.infraestructura.arquitectura.ServiceException;

@ControllerAdvice
public class ManejadorGlobalDeExcepciones extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<Object> handleCustomException(ServiceException se) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", se.getMessage());
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(body, headers, HttpStatus.CONFLICT); 
	}
}
