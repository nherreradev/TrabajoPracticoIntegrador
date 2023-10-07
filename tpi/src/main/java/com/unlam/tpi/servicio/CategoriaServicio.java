package com.unlam.tpi.servicio;

import java.util.List;

import javax.transaction.Transactional;

import com.unlam.tpi.dto.CategoriaDTO;

public interface CategoriaServicio {

	@Transactional
	public void guardar(CategoriaDTO categoria);

	@Transactional
	public CategoriaDTO obtener(Long id);

	@Transactional
	public void borrar(Long id);

	@Transactional
	public List<CategoriaDTO> listar();
}
