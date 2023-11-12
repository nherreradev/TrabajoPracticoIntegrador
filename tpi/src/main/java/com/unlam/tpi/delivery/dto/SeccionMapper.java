package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.Seccion;
import com.unlam.tpi.core.modelo.ServiceException;

public class SeccionMapper {

	private static ModelMapper mapper = new ModelMapper();

	public static Seccion dTOaEntidad(SeccionDTO seccion) {
		try {
			return mapper.map(seccion, Seccion.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir SeccionDTO a Seccion", e);
		}
	}

	public static SeccionDTO entidadADTO(Seccion seccion) {
		try {
			return mapper.map(seccion, SeccionDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir Seccion a SeccionDTO", e);
		}
	}

	public static List<SeccionDTO> entidadDTOLista(List<Seccion> secciones) {
		try {
			return secciones.stream().map(seccion -> mapper.map(seccion, SeccionDTO.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista Seccion a lista SeccionDTO", e);
		}
	}

	public static List<Seccion> traductorDeListaDTOaEntidad(List<SeccionDTO> secciones) throws ServiceException {
		try {
			return secciones.stream().map(seccion -> mapper.map(seccion, Seccion.class)).collect(Collectors.toList());

		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista SeccionDTO a lista Seccion", e);
		}
	}
}
