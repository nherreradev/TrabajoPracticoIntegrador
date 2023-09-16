package com.unlam.tpi.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.servicio.InstrumentoServicio;

@RestController
@RequestMapping("/api")
public class InstrumentoControladorImpl implements InstrumentoControlador {

	@Autowired
	InstrumentoServicio instrumentoServicio;
	
	@Override
	@GetMapping("/instrumentos")
	public String getInstrumentos() {
		instrumentoServicio.obtenerInstrumentos();
		
		return "Lista de instrumentos";
	}
	
}
