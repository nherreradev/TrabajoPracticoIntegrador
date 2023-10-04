package com.unlam.tpi.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlam.tpi.dto.CategoriaDTO;
import com.unlam.tpi.modelo.persistente.Categoria;
import com.unlam.tpi.repositorio.CategoriaRepositorio;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {

	@Autowired
	CategoriaRepositorio categoriaRepositorio;

	private ModelMapper mapper = new ModelMapper();
	
	private Categoria traductorDeDTOaDAO(CategoriaDTO categoria) {
		try {
			return mapper.map(categoria, Categoria.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir un objeto DTO a DAO", e);
		}
	}
	
	private CategoriaDTO traductorDeDAOaDTO(Categoria categoria) {
		try {
			return mapper.map(categoria, CategoriaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir un objeto DAO a DTO", e);
		}
	}
	
	private List<CategoriaDTO> traductorDeDAOaDTOLista(List<Categoria> categorias) {
		try {
			return categorias.stream().map(categoria -> mapper.map(categoria, CategoriaDTO.class))
				.collect(Collectors.toList());
		}catch (Exception e) {
			throw new ServiceException("Error en convertir una lista DAO a DTO", e);
		}
	}
	
	@Override
	public void guardar(CategoriaDTO categoria) {
		try {
			Categoria persistente = traductorDeDTOaDAO(categoria);
			getCategoriaRepositorio().save(persistente);
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la categoria", e);
		}
	}

	@Override
	public CategoriaDTO obtener(Long id) {
		try {
			return traductorDeDAOaDTO(getCategoriaRepositorio().getReferenceById(id));
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
		return traductorDeDAOaDTOLista(getCategoriaRepositorio().findAll());
	}
	
	public CategoriaRepositorio getCategoriaRepositorio() {
		return categoriaRepositorio;
	}

	public void setCategoriaRepositorio(CategoriaRepositorio categoriaRepositorio) {
		this.categoriaRepositorio = categoriaRepositorio;
	}
	
}
