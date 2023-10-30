package com.unlam.tpi.core.interfaces;

import java.util.List;

import com.unlam.tpi.core.modelo.Instrumento;

public interface IAServicio {

	void generarTXT(String tipo);

	List<Instrumento> obtenerPortafolioSugerido(String tipoPerfil);

}
