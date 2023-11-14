package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.Pregunta;
import com.unlam.tpi.core.modelo.ServiceException;

public class PreguntaMapper {

	private static ModelMapper mapper = new ModelMapper();

	public static Pregunta dTOaEntidad(PreguntaDTO pregunta) {
		try {
			return mapper.map(pregunta, Pregunta.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir PreguntaDTO a Pregunta", e);
		}
	}

	public static PreguntaDTO entidadADTO(Pregunta pregunta) {
		try {
			return mapper.map(pregunta, PreguntaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir Pregunta a PreguntaDTO", e);
		}
	}

	public static List<PreguntaDTO> entidadDTOLista(List<Pregunta> preguntas) {
		try {
			return preguntas.stream().map(pregunta -> mapper.map(pregunta, PreguntaDTO.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista Pregunta a lista PreguntaDTO", e);
		}
	}

	public static List<Pregunta> traductorDeListaDTOaEntidad(List<PreguntaDTO> preguntas) throws ServiceException {
		try {
			return preguntas.stream().map(pregunta -> mapper.map(pregunta, Pregunta.class))
					.collect(Collectors.toList());

		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista PreguntaDTO a lista Pregunta", e);
		}
	}
}
