package com.unlam.tpi.servicio;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.model.Contenido;
import com.unlam.tpi.model.ContenidoDTO;
import com.unlam.tpi.model.Pregunta;
import com.unlam.tpi.model.PreguntaDTO;
import com.unlam.tpi.repositorio.PreguntaRepositorio;

@Service
@Transactional
public class PreguntaServicioImpl implements PreguntaServicio {
	
	private ModelMapper mapper = new ModelMapper();
	
	@Autowired
	PreguntaRepositorio preguntaRepositorio;
	
	private Pregunta traductorDeDTOaDAO(PreguntaDTO pregunta) {
		return mapper.map(pregunta, Pregunta.class);
	}

	private PreguntaDTO traductorDeDAOaDTO(Pregunta pregunta) {
		return mapper.map(pregunta, PreguntaDTO.class);
	}
	
	private List<PreguntaDTO> traductorDeDAOaDTOLista(List<Pregunta> preguntas) {
		try {
			return preguntas.stream().map(pregunta -> mapper.map(pregunta, PreguntaDTO.class))
					.collect(Collectors.toList());
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void guardar(PreguntaDTO pregunta) {
		getPreguntaRepositorio().guardar(traductorDeDTOaDAO(pregunta));		
	}

	@Override
	public PreguntaDTO obtener(Long id) {
		return traductorDeDAOaDTO(getPreguntaRepositorio().obtener(id));
	}

	@Override
	public PreguntaDTO modificar(PreguntaDTO pregunta) {
		return traductorDeDAOaDTO(getPreguntaRepositorio().modificar(traductorDeDTOaDAO(pregunta)));
	}

	@Override
	public void borrar(Long id) {
		getPreguntaRepositorio().borrar(id);		
	}

	@Override
	public List<PreguntaDTO> listar() {
		return traductorDeDAOaDTOLista(getPreguntaRepositorio().listar());
	}

	public PreguntaRepositorio getPreguntaRepositorio() {
		return preguntaRepositorio;
	}

	public void setPreguntaRepositorio(PreguntaRepositorio preguntaRepositorio) {
		this.preguntaRepositorio = preguntaRepositorio;
	}
	
}
