package com.unlam.tpi.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.PreguntaDTO;
import com.unlam.tpi.helpers.TraductorGenerico;
import com.unlam.tpi.interfaces.PreguntaServicio;
import com.unlam.tpi.modelo.persistente.Pregunta;
import com.unlam.tpi.repositorio.PreguntaRepositorio;

@Service
public class PreguntaServicioImpl implements PreguntaServicio {
	
	@Autowired
	PreguntaRepositorio preguntaRepositorio;
	
	@Override
	public void guardar(PreguntaDTO pregunta) {
		try {
			Pregunta persistente = TraductorGenerico.traductorDeDTOaEntidad(pregunta, Pregunta.class);
			getPreguntaRepositorio().save(persistente);
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la pregunta", e);
		}
	}

	@Override
	public PreguntaDTO obtener(Long id) {
		try {
			return TraductorGenerico.traductorDeEntidadaDTO(getPreguntaRepositorio().getReferenceById(id), PreguntaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la pregunta", e);
		}
	}

	@Override
	public void borrar(Long id) {
		try {
			getPreguntaRepositorio().deleteById(id);		
		} catch (Exception e) {
			throw new ServiceException("Error al borrar la pregunta", e);
		}
	}

	@Override
	public List<PreguntaDTO> listar() {
		try {
			return TraductorGenerico.traductorDeListaEntidadaDTO(getPreguntaRepositorio().findAll(), PreguntaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error al listar las preguntas", e);
		}
	}
	
	@Override
	public List<PreguntaDTO> listarPorCategoria(String categoria) {
		try {
			return TraductorGenerico.traductorDeListaEntidadaDTO(getPreguntaRepositorio().findByCategoria_Nombre(categoria), PreguntaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error al listar las preguntas", e);
		}
	}

	public PreguntaRepositorio getPreguntaRepositorio() {
		return preguntaRepositorio;
	}
	public void setPreguntaRepositorio(PreguntaRepositorio preguntaRepositorio) {
		this.preguntaRepositorio = preguntaRepositorio;
	}
	
}
