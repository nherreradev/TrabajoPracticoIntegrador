package com.unlam.tpi.core.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.ListaPreciosIOL;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.delivery.dto.InstrumentoDTO;

@Service
public class ListaPreciosServicioImpl implements ListaPreciosServicio{

	@Autowired
	ListaPreciosIOL listaPreciosIOL;
	
	@Override
	public void guardarListaPrecios(String titulo, String token) {
		listaPreciosIOL.guardarListaPrecios(titulo, token);
	}

	@Override
	public List<Instrumento> getListaPrecioMongo(String titulo) {
		return listaPreciosIOL.getListaPrecioMongo(titulo);
	}
	
	
}
