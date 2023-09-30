package com.unlam.tpi.servicio;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.model.Contenido;
import com.unlam.tpi.model.ContenidoDTO;
import com.unlam.tpi.repositorio.ContenidoRepositorio;

@Service
@Transactional
public class ContenidoServicioImpl implements ContenidoServicio {

	@Autowired
	ContenidoRepositorio contenidoRepositorio;

	private ModelMapper mapper = new ModelMapper();
	
	private Contenido traductorDeDTOaDAO(ContenidoDTO Contenido) {
		return mapper.map(Contenido, Contenido.class);
	}

	private ContenidoDTO traductorDeDAOaDTO(Contenido Contenido) {
		return mapper.map(Contenido, ContenidoDTO.class);
	}

	private List<ContenidoDTO> traductorDeDAOaDTOLista(List<Contenido> contenidos) {
		try {
			return contenidos.stream().map(contenido -> mapper.map(contenido, ContenidoDTO.class))
					.collect(Collectors.toList());
			
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void guardar(ContenidoDTO Contenido) {
		Contenido persistente = traductorDeDTOaDAO(Contenido);
		getContenidoRepositorio().guardar(persistente);
	}

	@Override
	public ContenidoDTO obtener(Long id) {
		return traductorDeDAOaDTO(getContenidoRepositorio().obtener(id));
	}

	@Override
	public ContenidoDTO modificar(ContenidoDTO Contenido) {
		Contenido persistente = traductorDeDTOaDAO(Contenido);
		return traductorDeDAOaDTO(getContenidoRepositorio().modificar(persistente));
	}

	@Override
	public void borrar(Long id) {
		getContenidoRepositorio().borrar(id);
	}

	@Override
	public List<ContenidoDTO> listar() {
		try {
			return traductorDeDAOaDTOLista(getContenidoRepositorio().listar());
		} catch (Exception e) {
			throw e;
		}
	}

	public ContenidoRepositorio getContenidoRepositorio() {
		return contenidoRepositorio;
	}

	public void setContenidoRepositorio(ContenidoRepositorio contenidoRepositorio) {
		this.contenidoRepositorio = contenidoRepositorio;
	}

}
