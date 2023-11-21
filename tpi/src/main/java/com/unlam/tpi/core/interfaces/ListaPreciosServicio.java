package com.unlam.tpi.core.interfaces;

import java.util.List;

import javax.transaction.Transactional;

import com.unlam.tpi.core.modelo.Instrumento;

@Transactional
public interface ListaPreciosServicio {

	public void guardarListaPrecios(String titulo, String token);

	public List<Instrumento> getListaPrecio(String titulo);
}
