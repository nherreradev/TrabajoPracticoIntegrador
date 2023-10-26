package com.unlam.tpi.interfaces;

import org.springframework.http.ResponseEntity;

public interface PortafolioSugerenciaServicio {
	
	public ResponseEntity<String> obtenerRecomendacion();

}
