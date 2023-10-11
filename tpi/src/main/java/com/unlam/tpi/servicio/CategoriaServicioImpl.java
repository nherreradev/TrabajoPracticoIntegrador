package com.unlam.tpi.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.CategoriaDTO;
import com.unlam.tpi.helpers.TraductorGenerico;
import com.unlam.tpi.modelo.persistente.Categoria;
import com.unlam.tpi.repositorio.CategoriaRepositorio;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {

	@Autowired
	CategoriaRepositorio categoriaRepositorio;

	
	@Override
	public void guardar(CategoriaDTO categoria) {
		try {
			Categoria persistente = TraductorGenerico.traductorDeDTOaEntidad(categoria, Categoria.class);
			getCategoriaRepositorio().save(persistente);
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la categoria", e);
		}
	}

	@Override
	public CategoriaDTO obtener(Long id) {
		try {
			return TraductorGenerico.traductorDeEntidadaDTO(getCategoriaRepositorio().getReferenceById(id), CategoriaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la categoria", e);
		}
	}

	@Override
	public void borrar(Long id) {
		try {
			getCategoriaRepositorio().deleteById(id);
		} catch (Exception e) {
			throw new ServiceException("Error al borrar la categoria", e);
		}
	}

	@Override
	public List<CategoriaDTO> listar() {
		return TraductorGenerico.traductorDeListaEntidadaDTO(getCategoriaRepositorio().findAll(),CategoriaDTO.class);
	}
	
	public CategoriaRepositorio getCategoriaRepositorio() {
		return categoriaRepositorio;
	}

	public void setCategoriaRepositorio(CategoriaRepositorio categoriaRepositorio) {
		this.categoriaRepositorio = categoriaRepositorio;
	}
	
}
