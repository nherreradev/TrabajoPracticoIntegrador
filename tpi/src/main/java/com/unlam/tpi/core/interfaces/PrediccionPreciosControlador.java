package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.unlam.tpi.core.modelo.PrediccionPrecio;

public interface PrediccionPreciosControlador {

	ResponseEntity<PrediccionPrecio> getDolar() throws JsonMappingException, JsonProcessingException;

}
