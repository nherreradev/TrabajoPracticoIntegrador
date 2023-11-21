package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.core.modelo.Instrumento;

public interface IAControlador {
	
	public ResponseEntity<String> generarArchivoTXT(String tipo) throws IOException;

	public ResponseEntity<List<Instrumento>> obtenerPortafolioSugerido(String tipoPerfil, int idProducto) throws IOException, KeyManagementException, NoSuchAlgorithmException;
	
	public ResponseEntity<String> obtenerPortafolioSugeridoFake(String tipoPerfil);

}
