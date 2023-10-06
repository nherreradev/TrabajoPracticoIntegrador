package com.unlam.tpi.servicio;

import java.util.List;

import com.unlam.tpi.dto.CategoriaDTO;

public interface CategoriaServicio {

	public void guardar(CategoriaDTO categoria);

	public CategoriaDTO obtener(Long id);

	public void borrar(Long id);

	public List<CategoriaDTO> listar();
}
