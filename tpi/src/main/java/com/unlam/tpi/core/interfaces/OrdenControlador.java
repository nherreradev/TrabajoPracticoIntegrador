package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.delivery.dto.OrdenDTO;

public interface OrdenControlador {

	public ResponseEntity<String> capturarOrden(String headerAuthorization, OrdenDTO orden) throws JsonProcessingException;

}
