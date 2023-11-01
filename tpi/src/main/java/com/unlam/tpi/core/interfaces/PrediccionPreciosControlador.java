package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.delivery.dto.PrediccionPrecioDTO;

public interface PrediccionPreciosControlador {

	ResponseEntity<PrediccionPrecioDTO> getDolar();

}
