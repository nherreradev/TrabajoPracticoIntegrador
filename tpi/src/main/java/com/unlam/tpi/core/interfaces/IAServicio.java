package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.net.ProtocolException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.unlam.tpi.core.modelo.Instrumento;

public interface IAServicio {

	void generarTXT(String tipo) throws IOException;

	List<Instrumento> obtenerPortafolioSugeridoFake(String tipoPerfil);
	
	List<Instrumento> obtenerPortafolioSugerido(String tipoPerfil, int idProducto) throws IOException, KeyManagementException, NoSuchAlgorithmException;

}
