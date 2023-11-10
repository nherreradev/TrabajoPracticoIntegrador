package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.Categoria;
import com.unlam.tpi.core.modelo.ServiceException;

public class CategoriaMapper {

	private static ModelMapper mapper = new ModelMapper();

	public static Categoria dTOaEntidad(CategoriaDTO categoria) {
		try {
			return mapper.map(categoria, Categoria.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir CategoriaDTO a Categoria", e);
		}
	}

	public static CategoriaDTO entidadADTO(Categoria categoria) {
		try {
			return mapper.map(categoria, CategoriaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir Categoria a CategoriaDTO", e);
		}
	}

	public static List<CategoriaDTO> entidadDTOLista(List<Categoria> categorias) {
		try {
			return categorias.stream().map(categoria -> mapper.map(categoria, CategoriaDTO.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista Categoria a lista CategoriaDTO", e);
		}
	}

	public static List<Categoria> traductorDeListaDTOaEntidad(List<CategoriaDTO> categorias) throws ServiceException {
		try {
			return categorias.stream().map(categoria -> mapper.map(categoria, Categoria.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista CategoriaDTO a lista Categoria", e);
		}
	}
}
