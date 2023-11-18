package com.unlam.tpi.core.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.unlam.tpi.core.interfaces.PrediccionPrecioApi;
import com.unlam.tpi.core.interfaces.PrediccionPrecioServicio;
import com.unlam.tpi.delivery.dto.PrediccionPrecioDTO;

@Service
public class PrediccionPrecioServicioImpl implements PrediccionPrecioServicio {

	@Autowired
	private PrediccionPrecioApi prediccionPrecioApi;
	
	@Override
	public PrediccionPrecioDTO obtenerCotizacionDolar() throws JsonMappingException, JsonProcessingException {
		return getPrediccionPrecioApi().obtenerPrecio();
	}
	
	public PrediccionPrecioApi getPrediccionPrecioApi() {
		return prediccionPrecioApi;
	}
	public void setPrediccionPrecioApi(PrediccionPrecioApi prediccionPrecioApi) {
		this.prediccionPrecioApi = prediccionPrecioApi;
	}
	
	

}

