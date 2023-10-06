package com.unlam.tpi.servicio;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.dto.PreguntaDTO;
import com.unlam.tpi.modelo.persistente.Pregunta;
import com.unlam.tpi.repositorio.PreguntaRepositorio;

@Service
@Transactional
public class PreguntaServicioImpl implements PreguntaServicio {
	
	private ModelMapper mapper = new ModelMapper();
	
	@Autowired
	PreguntaRepositorio preguntaRepositorio;
	
	private Pregunta traductorDeDTOaDAO(PreguntaDTO pregunta) {
		try {
			return mapper.map(pregunta, Pregunta.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir un objeto DTO a DAO", e);
		}
	}

	private PreguntaDTO traductorDeDAOaDTO(Pregunta pregunta) {
		try {
			return mapper.map(pregunta, PreguntaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir un objeto DAO a DTO", e);
		}
	}
	
	private List<PreguntaDTO> traductorDeDAOaDTOLista(List<Pregunta> preguntas) {
		try {
			return preguntas.stream().map(pregunta -> mapper.map(pregunta, PreguntaDTO.class))
					.collect(Collectors.toList());
		}catch (Exception e) {
			throw new ServiceException("Error en convertir una lista DAO a DTO", e);
		}
	}

	@Override
	public void guardar(PreguntaDTO pregunta) {
		try {
			Pregunta persistente = traductorDeDTOaDAO(pregunta);
			getPreguntaRepositorio().save(persistente);
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la pregunta", e);
		}
	}

	@Override
	public PreguntaDTO obtener(Long id) {
		try {
			return traductorDeDAOaDTO(getPreguntaRepositorio().getReferenceById(id));
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
		return traductorDeDAOaDTOLista(getPreguntaRepositorio().findAll());
	}

	public PreguntaRepositorio getPreguntaRepositorio() {
		return preguntaRepositorio;
	}

	public void setPreguntaRepositorio(PreguntaRepositorio preguntaRepositorio) {
		this.preguntaRepositorio = preguntaRepositorio;
	}
	
}
