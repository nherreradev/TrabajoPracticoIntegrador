package com.unlam.tpi.delivery.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.core.interfaces.IAControlador;
import com.unlam.tpi.core.interfaces.IAServicio;

@CrossOrigin
@RestController
@RequestMapping("/IA")
public class IAControladorImpl implements IAControlador {

	@Autowired
	IAServicio iAServicio;
	
	@Override
	@GetMapping("/txt")
	public ResponseEntity<String> generarArchivoTXT(String tipo) {
		iAServicio.generarTXT(tipo);
		return null;
	}

	
	
	
}
