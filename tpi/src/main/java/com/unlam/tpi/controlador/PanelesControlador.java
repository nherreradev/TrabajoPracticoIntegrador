package com.unlam.tpi.controlador;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.model.Instrumento;

public interface PanelesControlador {
	public ResponseEntity<Map<String, Instrumento>> getPanelDeAcciones();
	
	public ResponseEntity<Map<String, Instrumento>> getPanelDeBonos();
	
	
}
