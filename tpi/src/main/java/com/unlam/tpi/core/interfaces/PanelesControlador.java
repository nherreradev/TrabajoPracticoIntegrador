package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

public interface PanelesControlador {
	public ResponseEntity<String> getPanelDeAcciones();
	
	public ResponseEntity<String> getPanelDeBonos();

	public ResponseEntity<String> getPanelDeCedears();
	
	
}
