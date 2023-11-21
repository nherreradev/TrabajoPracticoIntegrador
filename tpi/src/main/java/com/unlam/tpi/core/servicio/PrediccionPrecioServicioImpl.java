package com.unlam.tpi.core.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.PrediccionPrecioApi;
import com.unlam.tpi.core.interfaces.PrediccionPrecioServicio;
import com.unlam.tpi.core.modelo.PrediccionPrecio;

@Service
public class PrediccionPrecioServicioImpl implements PrediccionPrecioServicio {

	@Autowired
	private PrediccionPrecioApi prediccionPrecioApi;
	
	@Override
	public PrediccionPrecio obtenerCotizacionDolar() {
		return getPrediccionPrecioApi().obtenerPrecio();
	}
	
	public PrediccionPrecioApi getPrediccionPrecioApi() {
		return prediccionPrecioApi;
	}
	public void setPrediccionPrecioApi(PrediccionPrecioApi prediccionPrecioApi) {
		this.prediccionPrecioApi = prediccionPrecioApi;
	}
	
	

}

