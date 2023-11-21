package com.unlam.tpi.core.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.core.modelo.Instrumento;

public interface ListaPreciosAPI {
	
	public void guardarListaPrecios(String titulo, String token);

	public Map<String, Boolean> validateResponse(ResponseEntity<String> responseEntity, String instrumento);

	public List<Instrumento> getListaPrecio(String instrumento);
}
