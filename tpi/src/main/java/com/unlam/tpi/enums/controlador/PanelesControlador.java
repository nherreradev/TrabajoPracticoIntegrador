package com.unlam.tpi.enums.controlador;

import org.springframework.http.ResponseEntity;

public interface PanelesControlador {
	public ResponseEntity<String> getPanelDeAcciones();
	
	public ResponseEntity<String> getPanelDeBonos();
	
	
}
