package com.unlam.tpi.core.servicio;

import org.springframework.http.ResponseEntity;

public interface PortafolioSugerenciaServicio {
	
	public ResponseEntity<String> obtenerRecomendacion();

}
