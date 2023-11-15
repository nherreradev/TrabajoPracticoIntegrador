package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.Categoria;
import com.unlam.tpi.core.modelo.ServiceException;

public class CategoriaMapper {

	private static ModelMapper mapper = new ModelMapper();

	public static Categoria dTOaEntidad(CategoriaDTO categoria) {
		return mapper.map(categoria, Categoria.class);
	}

	public static CategoriaDTO entidadADTO(Categoria categoria) {
		return mapper.map(categoria, CategoriaDTO.class);
	}

	public static List<CategoriaDTO> entidadDTOLista(List<Categoria> categorias) {
		return categorias.stream().map(categoria -> mapper.map(categoria, CategoriaDTO.class))
				.collect(Collectors.toList());
	}

	public static List<Categoria> traductorDeListaDTOaEntidad(List<CategoriaDTO> categorias) throws ServiceException {
		return categorias.stream().map(categoria -> mapper.map(categoria, Categoria.class))
				.collect(Collectors.toList());
	}
}