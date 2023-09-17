package com.unlam.tpi.controlador;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.unlam.tpi.model.Instrumento;

public interface PanelesControlador {
	public ResponseEntity<Map<String, Instrumento>> getPanelDeAcciones()
			throws JsonMappingException, JsonProcessingException;
}
