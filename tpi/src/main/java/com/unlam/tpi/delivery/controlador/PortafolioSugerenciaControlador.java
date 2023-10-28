package com.unlam.tpi.delivery.controlador;

import org.springframework.http.ResponseEntity;

public interface PortafolioSugerenciaControlador {
	public ResponseEntity<String> obtenerRecomendacion();
}
