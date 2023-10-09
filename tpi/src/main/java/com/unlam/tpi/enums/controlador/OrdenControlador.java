package com.unlam.tpi.enums.controlador;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.dto.OrdenDTO;

public interface OrdenControlador {

	public ResponseEntity<String> capturarOrden(OrdenDTO orden);

}
