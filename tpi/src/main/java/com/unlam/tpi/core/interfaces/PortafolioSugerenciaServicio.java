package com.unlam.tpi.core.interfaces;

import java.net.ProtocolException;

public interface PortafolioSugerenciaServicio {
	
	public String obtenerRecomendacion(String tipoPerfil) throws ProtocolException;

}
