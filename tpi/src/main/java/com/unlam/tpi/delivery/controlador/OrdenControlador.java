package com.unlam.tpi.delivery.controlador;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.delivery.dto.OrdenDTO;

public interface OrdenControlador {

	public ResponseEntity<String> capturarOrden(OrdenDTO orden);

}
