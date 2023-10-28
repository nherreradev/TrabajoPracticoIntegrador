package com.unlam.tpi.delivery.controlador;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.infraestructura.modelo.RequestCargaDeDinero;
import com.unlam.tpi.infraestructura.modelo.ValuacionTotalRespuesta;

public interface CarteraControlador {
	
	public ResponseEntity<ValuacionTotalRespuesta> getValuacionTotal();
	
	public ResponseEntity<String> acreditarDinero(RequestCargaDeDinero request);
}
