package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface PortafolioSugerenciaServicio {
	
	public String obtenerRecomendacion(String tipoPerfil, int idProducto) throws IOException, KeyManagementException, NoSuchAlgorithmException;

}
