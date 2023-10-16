package com.unlam.tpi.controlador;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.modelo.rest.ValuacionTotalRespuesta;

public interface CarteraControlador {
	
	public ResponseEntity<ValuacionTotalRespuesta> getValuacionTotal();
	
	public ResponseEntity<String> acreditarDinero();
}
