package com.unlam.tpi.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlam.tpi.model.Categoria;
import com.unlam.tpi.model.CategoriaDTO;
import com.unlam.tpi.repositorio.CategoriaRepositorio;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {

	@Autowired
	CategoriaRepositorio categoriaRepositorio;

	private ModelMapper mapper = new ModelMapper();
	private ObjectMapper om = new ObjectMapper();
	
	private Categoria traductorDeDTOaDAO(CategoriaDTO categoria) {
		return mapper.map(categoria, Categoria.class);
	}
	
	private CategoriaDTO traductorDeDAOaDTO(Categoria categoria) {
		return mapper.map(categoria, CategoriaDTO.class);
	}
	
	private List<CategoriaDTO> traductorDeDAOaDTOLista(List<Categoria> categorias) {
		return categorias.stream().map(categoria -> mapper.map(categoria, CategoriaDTO.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public void guardar(CategoriaDTO categoria) {
		Categoria persistente = traductorDeDTOaDAO(categoria);
		getCategoriaRepositorio().guardar(persistente);
	}

	@Override
	public CategoriaDTO obtener(Long id) {
		return traductorDeDAOaDTO(getCategoriaRepositorio().obtener(id));
	}

	@Override
	public CategoriaDTO modificar(CategoriaDTO categoria) {
		Categoria persistente = traductorDeDTOaDAO(categoria);
		return traductorDeDAOaDTO(getCategoriaRepositorio().modificar(persistente));
	}

	@Override
	public void borrar(Long id) {
		getCategoriaRepositorio().borrar(id);
	}

	@Override
	public List<CategoriaDTO> listar() {
		return traductorDeDAOaDTOLista(getCategoriaRepositorio().listar());
	}
	
	public CategoriaRepositorio getCategoriaRepositorio() {
		return categoriaRepositorio;
	}

	public void setCategoriaRepositorio(CategoriaRepositorio categoriaRepositorio) {
		this.categoriaRepositorio = categoriaRepositorio;
	}
	
}
