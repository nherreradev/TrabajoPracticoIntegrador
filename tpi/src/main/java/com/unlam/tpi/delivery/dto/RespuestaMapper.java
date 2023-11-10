package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.Respuesta;
import com.unlam.tpi.core.modelo.ServiceException;

public class RespuestaMapper {

	private static ModelMapper mapper = new ModelMapper();
	
	public static Respuesta dTOaEntidad(RespuestaDTO respuesta) {
		try {
			return mapper.map(respuesta, Respuesta.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir RespuestaDTO a Respuesta", e);
		}
	}

	public static RespuestaDTO entidadADTO(Respuesta respuesta) {
		try {
			return mapper.map(respuesta, RespuestaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir Respuesta a RespuestaDTO", e);
		}
	}

	public static List<RespuestaDTO> entidadDTOLista(List<Respuesta> respuestas) {
		try {
			return respuestas.stream().map(respuesta -> mapper.map(respuesta, RespuestaDTO.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista Respuesta a lista RespuestaDTO", e);
		}
	}

	public static List<Respuesta> traductorDeListaDTOaEntidad(List<RespuestaDTO> respuestas) throws ServiceException {
		try {
			return respuestas.stream().map(respuesta -> mapper.map(respuesta, Respuesta.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista RespuestaDTO a lista Respuesta", e);
		}
	}
}
