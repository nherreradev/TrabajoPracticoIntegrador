package com.unlam.tpi.core.servicio;

import java.util.List;

import com.unlam.tpi.core.modelo.Instrumento;

public interface ListaPreciosServicio {

	public void guardarListaPrecios(String titulo, String token);

	public List<Instrumento> getListaPrecioMongo(String titulo);
}
