package com.unlam.tpi.helpers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.arquitectura.ServiceException;

public class TraductorGenerico {

	private static final ModelMapper modelMapper = new ModelMapper();

	private TraductorGenerico() {
	}

	public static <S, T> T traductorDeEntidadaDTO(S entidad, Class<T> dtoClass) throws ServiceException {
		try {
			return modelMapper.map(entidad, dtoClass);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir un objeto DAO a DTO", e);
		}
	}

	public static <S, T> List<T> traductorDeListaEntidadaDTO(List<S> objetList, Class<T> dtoClass)
			throws ServiceException {
		try {
			return objetList.stream()
	                .map(dto -> modelMapper.map(dto, dtoClass))
	                .collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista DAO a DTO", e);
		}

	}

	public static <S, T> S traductorDeDTOaEntidad(T entidad, Class<S> dtoClass) throws ServiceException {
		try {
			return modelMapper.map(entidad, dtoClass);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista DTO a DAO", e);
		}
	}

	public static <S, T> List<S> traductorDeListaDTOaEntidad(List<T> objetList, Class<S> entidad)
			throws ServiceException {
		try {
			return objetList.stream()
	                .map(entity -> modelMapper.map(entity, entidad))
	                .collect(Collectors.toList());

		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista DTO a DAO", e);
		}
	}

}
