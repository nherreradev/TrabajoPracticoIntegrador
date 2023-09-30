package com.unlam.tpi.controlador;

import java.util.List;

import com.unlam.tpi.model.CategoriaDTO;

public interface CategoriaControlador {

    public void guardar(CategoriaDTO categoria);

    public CategoriaDTO obtener(Long id);

    public CategoriaDTO modificar(CategoriaDTO categoria);

    public void borrar(Long id);

	public List<CategoriaDTO> listar();
}
