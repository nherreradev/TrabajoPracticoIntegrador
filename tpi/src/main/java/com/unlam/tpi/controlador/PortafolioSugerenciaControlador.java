package com.unlam.tpi.controlador;

import org.springframework.http.ResponseEntity;

public interface PortafolioSugerenciaControlador {
	public ResponseEntity<String> obtenerRecomendacion();
}
