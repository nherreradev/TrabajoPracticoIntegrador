package com.unlam.tpi.core.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.RecomendacionPortafolioAPI;
import com.unlam.tpi.core.interfaces.RecomendacionPortafolioServicio;
import com.unlam.tpi.core.modelo.Instrumento;

@Service
public class RecomendacionPortafolioServicioImpl implements RecomendacionPortafolioServicio{
	
	@Autowired
	private RecomendacionPortafolioAPI recomendacionPortafolioAPI;

	@Override
	public void generarTXT(String tipo) {
		recomendacionPortafolioAPI.generarTXT(tipo);
	}

	@Override
	public List<Instrumento> obtenerPortafolioSugeridoFake(String tipoPerfil) {
		return recomendacionPortafolioAPI.obtenerPortafolioSugeridoFake(tipoPerfil);
	}

	@Override
	public List<Instrumento> obtenerPortafolioSugerido(String tipoPerfil, int idProducto) {
		return recomendacionPortafolioAPI.obtenerPortafolioSugerido(tipoPerfil, idProducto);
	}
}
