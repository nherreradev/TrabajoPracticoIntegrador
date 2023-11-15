package com.unlam.tpi.core.interfaces;

import java.io.IOException;

public interface PortafolioSugerenciaServicio {
	
	public String obtenerRecomendacion(String tipoPerfil, int idProducto) throws IOException;

}
