package com.unlam.tpi.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.dto.OrdenDTO;
import com.unlam.tpi.servicio.OrdenServicio;

@RestController
@RequestMapping("/orden")
public class OrdenControladorImpl implements OrdenControlador {

	@Autowired
	OrdenServicio ordenServicio;

	@Override
	@PostMapping("/capturar")
	public ResponseEntity<String> capturarOrden(@RequestBody OrdenDTO orden) {
		ordenServicio.capturarOrden(orden);
		return ResponseEntity.ok("Orden creada correctamente");
	}
}
