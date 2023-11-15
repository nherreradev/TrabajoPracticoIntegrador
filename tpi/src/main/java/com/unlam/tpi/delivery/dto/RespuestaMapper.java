package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.Respuesta;
import com.unlam.tpi.core.modelo.ServiceException;

public class RespuestaMapper {

	private static ModelMapper mapper = new ModelMapper();

	public static Respuesta dTOaEntidad(RespuestaDTO respuesta) {
		return mapper.map(respuesta, Respuesta.class);
	}

	public static RespuestaDTO entidadADTO(Respuesta respuesta) {
		return mapper.map(respuesta, RespuestaDTO.class);
	}

	public static List<RespuestaDTO> entidadDTOLista(List<Respuesta> respuestas) {
		return respuestas.stream().map(respuesta -> mapper.map(respuesta, RespuestaDTO.class))
				.collect(Collectors.toList());
	}

	public static List<Respuesta> traductorDeListaDTOaEntidad(List<RespuestaDTO> respuestas) throws ServiceException {
		return respuestas.stream().map(respuesta -> mapper.map(respuesta, Respuesta.class))
				.collect(Collectors.toList());
	}
}