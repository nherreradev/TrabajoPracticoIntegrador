package com.unlam.tpi.core.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.unlam.tpi.delivery.dto.PrediccionPrecioDTO;

public interface PrediccionPrecioServicio {

	public PrediccionPrecioDTO obtenerCotizacionDolar() throws JsonMappingException, JsonProcessingException;
}
