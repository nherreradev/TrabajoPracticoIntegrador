package com.unlam.tpi.core.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.ListaPreciosAPI;
import com.unlam.tpi.core.interfaces.ListaPreciosServicio;
import com.unlam.tpi.core.modelo.Instrumento;

@Service
public class ListaPreciosServicioImpl implements ListaPreciosServicio{

	@Autowired
	ListaPreciosAPI listaPreciosApi;
	
	@Override
	public void guardarListaPrecios(String titulo, String token) {
		listaPreciosApi.guardarListaPrecios(titulo, token);
	}

	@Override
	public List<Instrumento> getListaPrecio(String titulo) {
		return listaPreciosApi.getListaPrecio(titulo);
	}
	
	
}