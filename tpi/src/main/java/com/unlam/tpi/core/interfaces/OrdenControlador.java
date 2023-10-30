package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.delivery.dto.OrdenDTO;

public interface OrdenControlador {

	public ResponseEntity<String> capturarOrden(OrdenDTO orden);

}