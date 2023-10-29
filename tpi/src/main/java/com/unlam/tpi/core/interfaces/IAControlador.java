package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

public interface IAControlador {
	public ResponseEntity<String> generarArchivoTXT(String tipo);
	
}
