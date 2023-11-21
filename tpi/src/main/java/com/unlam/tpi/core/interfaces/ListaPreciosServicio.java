package com.unlam.tpi.core.interfaces;

import java.util.List;

import com.unlam.tpi.core.modelo.Instrumento;

public interface ListaPreciosServicio {

	public void guardarListaPrecios(String titulo, String token);

	public List<Instrumento> getListaPrecio(String titulo);
}
