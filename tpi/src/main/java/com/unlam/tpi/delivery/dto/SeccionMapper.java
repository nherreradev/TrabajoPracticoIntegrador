package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.Seccion;
import com.unlam.tpi.core.modelo.ServiceException;

public class SeccionMapper {

	private static ModelMapper mapper = new ModelMapper();

	public static Seccion dTOaEntidad(SeccionDTO seccion) {
		return mapper.map(seccion, Seccion.class);
	}

	public static SeccionDTO entidadADTO(Seccion seccion) {
		return mapper.map(seccion, SeccionDTO.class);
	}

	public static List<SeccionDTO> entidadDTOLista(List<Seccion> secciones) {
		return secciones.stream().map(seccion -> mapper.map(seccion, SeccionDTO.class)).collect(Collectors.toList());
	}

	public static List<Seccion> traductorDeListaDTOaEntidad(List<SeccionDTO> secciones) throws ServiceException {
		return secciones.stream().map(seccion -> mapper.map(seccion, Seccion.class)).collect(Collectors.toList());
	}
}