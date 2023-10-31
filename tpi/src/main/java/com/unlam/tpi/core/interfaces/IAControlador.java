package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

public interface IAControlador {
	
	public ResponseEntity<String> generarArchivoTXT(String tipo);

	public ResponseEntity<String> obtenerPortafolioSugerido(String tipoPerfil, String url);
	
	public ResponseEntity<String> obtenerPortafolioSugeridoFake(String tipoPerfil);

}
