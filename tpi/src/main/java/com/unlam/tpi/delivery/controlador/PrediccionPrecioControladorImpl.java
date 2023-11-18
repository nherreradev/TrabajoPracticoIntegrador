package com.unlam.tpi.delivery.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.unlam.tpi.core.interfaces.PrediccionPrecioServicio;
import com.unlam.tpi.core.interfaces.PrediccionPreciosControlador;
import com.unlam.tpi.delivery.dto.PrediccionPrecioDTO;

@CrossOrigin
@RestController
@RequestMapping("/prediccion")
public class PrediccionPrecioControladorImpl implements PrediccionPreciosControlador{
	
	@Autowired
	private PrediccionPrecioServicio prediccionPreciosServicio;
	
	@Override
	@GetMapping("/dolar")
	public ResponseEntity<PrediccionPrecioDTO> getDolar() throws JsonMappingException, JsonProcessingException {
		return ResponseEntity.ok(prediccionPreciosServicio.obtenerCotizacionDolar());
	}
}
