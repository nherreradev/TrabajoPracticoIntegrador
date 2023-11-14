package com.unlam.tpi.infraestructura.manejoExcepciones;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ManejadorGlobalDeExcepciones extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleCustomException(Exception se) {
		LogManager.getLogger().error("Ocurrió un error en el servicio",se);
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Ocurrió un error en el servicio: " + se.getMessage());
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(body, headers, HttpStatus.INTERNAL_SERVER_ERROR); 
	}
}
