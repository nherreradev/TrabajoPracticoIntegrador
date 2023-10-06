package com.unlam.tpi.helpers;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.google.protobuf.ServiceException;

public class TraductorGenerico {

	private static final ModelMapper modelMapper = new ModelMapper();

	private TraductorGenerico() {
	}

	public static <S, T> T traductorDeDAOaDTO(S source, Class<T> targetClass) throws ServiceException {
		try {
			return modelMapper.map(source, targetClass);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir un objeto DAO a DTO", e);
		}
	}

	public static <S, T> List<T> traductorDeListaDAOaDTO(List<S> sourceList, Class<T> targetClass)
			throws ServiceException {
		try {
			Type listType = new TypeToken<List<T>>() {
			}.getType();
			return modelMapper.map(sourceList, listType);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista DAO a DTO", e);
		}

	}

	public static <S, T> S traductorDeDTOaDAO(T source, Class<S> targetClass) throws ServiceException {
		try {
			return modelMapper.map(source, targetClass);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista DTO a DAO", e);
		}
	}

	public static <S, T> List<S> traductorDeListaDTOaDAO(List<T> sourceList, Class<S> targetClass)
			throws ServiceException {
		try {
			Type listType = new TypeToken<List<S>>() {
			}.getType();
			return modelMapper.map(sourceList, listType);

		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista DTO a DAO", e);
		}
	}

}
