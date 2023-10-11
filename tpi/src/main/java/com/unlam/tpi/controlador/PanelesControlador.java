package com.unlam.tpi.controlador;

import org.springframework.http.ResponseEntity;

public interface PanelesControlador {
	public ResponseEntity<String> getPanelDeAcciones();
	
	public ResponseEntity<String> getPanelDeBonos();
	
	
}
