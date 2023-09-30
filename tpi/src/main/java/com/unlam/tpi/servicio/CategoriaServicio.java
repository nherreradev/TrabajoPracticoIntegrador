package com.unlam.tpi.servicio;

import java.util.List;

import com.unlam.tpi.model.CategoriaDTO;

public interface CategoriaServicio {

    public void guardar(CategoriaDTO categoria);

    public CategoriaDTO obtener(Long id);

    public CategoriaDTO modificar(CategoriaDTO categoria);

    public void borrar(Long id);

	public List<CategoriaDTO> listar();
}
