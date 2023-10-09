package com.unlam.tpi.enums.controlador;

import java.util.List;

import com.unlam.tpi.dto.CategoriaDTO;


public interface CategoriaControlador {

    public void guardar(CategoriaDTO categoria);

    public CategoriaDTO obtener(Long id);

    public void borrar(Long id);

	public List<CategoriaDTO> listar();
}
