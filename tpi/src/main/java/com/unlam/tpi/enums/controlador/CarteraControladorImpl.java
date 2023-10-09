package com.unlam.tpi.enums.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.modelo.rest.ValuacionTotalRespuesta;
import com.unlam.tpi.servicio.PosicionServicio;

@RestController
@RequestMapping("/cartera")
public class CarteraControladorImpl implements CarteraControlador {

	@Autowired
	PosicionServicio posicionServicio;

	@Override
	@GetMapping("/valuacion/total")
	public ResponseEntity<ValuacionTotalRespuesta> getValuacionTotal() {
		ValuacionTotalRespuesta valuacionTotalRespuesta = posicionServicio.getValuacionTotal();
		return ResponseEntity.ok(valuacionTotalRespuesta);
	}

}
