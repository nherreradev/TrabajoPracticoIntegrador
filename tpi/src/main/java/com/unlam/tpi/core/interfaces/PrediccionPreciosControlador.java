package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.unlam.tpi.delivery.dto.PrediccionPrecioDTO;

public interface PrediccionPreciosControlador {

	ResponseEntity<PrediccionPrecioDTO> getDolar() throws JsonMappingException, JsonProcessingException;

}
