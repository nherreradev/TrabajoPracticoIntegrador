package com.unlam.tpi.controlador;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.dto.OrdenDTO;

public interface OrdenControlador {

	public ResponseEntity<OrdenDTO> capturarOrden(OrdenDTO orden);

}
