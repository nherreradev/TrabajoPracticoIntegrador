package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ListaPreciosServicio {
	
	public ResponseEntity<String> guardarListaPrecios(String titulo, String token);

	public Map<String, Boolean> validateResponse(ResponseEntity<String> responseEntity, String instrumento);

	public String getListaPrecioMongo(String instrumento);
}
