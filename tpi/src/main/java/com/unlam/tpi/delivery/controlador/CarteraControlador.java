package com.unlam.tpi.delivery.controlador;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.core.modelo.RequestCargaDeDinero;
import com.unlam.tpi.core.modelo.ValuacionTotalRespuesta;

public interface CarteraControlador {
	
	public ResponseEntity<ValuacionTotalRespuesta> getValuacionTotal();
	
	public ResponseEntity<String> acreditarDinero(RequestCargaDeDinero request);
}
