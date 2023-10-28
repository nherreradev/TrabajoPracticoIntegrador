package com.unlam.tpi.delivery.controlador;

import java.net.ProtocolException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.unlam.tpi.core.interfaces.PortafolioSugerenciaServicio;

@CrossOrigin
@RestController
@RequestMapping("/recomendacion")
public class PortafolioSugerenciaControladorImpl implements PortafolioSugerenciaControlador {

	@Autowired
	PortafolioSugerenciaServicio portafolioSugerenciaServicio;

	@Override
	@GetMapping("/portafolio")
	public ResponseEntity<String> obtenerRecomendacion() {

		String response = null;
		try {
			response = portafolioSugerenciaServicio.obtenerRecomendacion();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = new Gson().toJson(response);
		return ResponseEntity.ok(json);
	}

}
