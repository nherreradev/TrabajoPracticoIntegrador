package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.springframework.http.ResponseEntity;

public interface IAControlador {
	
	public ResponseEntity<String> generarArchivoTXT(String tipo) throws IOException;

	public ResponseEntity<String> obtenerPortafolioSugerido(String tipoPerfil, int idProducto) throws IOException, KeyManagementException, NoSuchAlgorithmException;
	
	public ResponseEntity<String> obtenerPortafolioSugeridoFake(String tipoPerfil);

}
