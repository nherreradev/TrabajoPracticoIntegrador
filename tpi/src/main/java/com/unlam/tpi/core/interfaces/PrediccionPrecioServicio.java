package com.unlam.tpi.core.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.unlam.tpi.core.modelo.PrediccionPrecio;

public interface PrediccionPrecioServicio {

	public PrediccionPrecio obtenerCotizacionDolar() throws JsonMappingException, JsonProcessingException;
}
