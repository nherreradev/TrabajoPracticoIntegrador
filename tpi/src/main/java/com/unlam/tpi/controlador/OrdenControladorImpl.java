package com.unlam.tpi.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.OrdenDTO;
import com.unlam.tpi.interfaces.OrdenServicio;

@CrossOrigin
@RestController
@RequestMapping("/orden")
public class OrdenControladorImpl implements OrdenControlador {

	@Autowired
	OrdenServicio ordenServicio;

	@Override
	@PostMapping("/capturar")
	public ResponseEntity<String> capturarOrden(@RequestBody OrdenDTO orden) {
		 try {
	            ordenServicio.capturarOrden(orden);
	            return ResponseEntity.ok("Orden creada correctamente");
	        } catch (ServiceException se) {
	            String errorMessage = "Error al crear la orden: " + se.getMessage();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	        }
	}
}
