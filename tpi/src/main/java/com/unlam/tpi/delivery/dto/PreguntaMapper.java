package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.Pregunta;
import com.unlam.tpi.core.modelo.ServiceException;

public class PreguntaMapper {

	private static ModelMapper mapper = new ModelMapper();

	public static Pregunta dTOaEntidad(PreguntaDTO pregunta) {
		return mapper.map(pregunta, Pregunta.class);
	}

	public static PreguntaDTO entidadADTO(Pregunta pregunta) {
		return mapper.map(pregunta, PreguntaDTO.class);
	}

	public static List<PreguntaDTO> entidadDTOLista(List<Pregunta> preguntas) {
		return preguntas.stream().map(pregunta -> mapper.map(pregunta, PreguntaDTO.class)).collect(Collectors.toList());
	}

	public static List<Pregunta> traductorDeListaDTOaEntidad(List<PreguntaDTO> preguntas) throws ServiceException {
		return preguntas.stream().map(pregunta -> mapper.map(pregunta, Pregunta.class)).collect(Collectors.toList());
	}
}