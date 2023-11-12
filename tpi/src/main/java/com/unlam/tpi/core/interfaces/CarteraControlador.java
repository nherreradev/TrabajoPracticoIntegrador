package com.unlam.tpi.core.interfaces;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.modelo.RequestCargaDeDinero;
import com.unlam.tpi.core.modelo.ValuacionTotalRespuesta;

public interface CarteraControlador {
	
	public ResponseEntity<ValuacionTotalRespuesta> getValuacionTotal(String token) throws JsonProcessingException;
	
	public ResponseEntity<String> acreditarDinero(String headerAuthorization, RequestCargaDeDinero request) throws JsonProcessingException;

	
	
}
