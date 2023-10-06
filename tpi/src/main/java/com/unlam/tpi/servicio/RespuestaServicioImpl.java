package com.unlam.tpi.servicio;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.dto.RespuestaDTO;
import com.unlam.tpi.modelo.persistente.Respuesta;
import com.unlam.tpi.repositorio.RespuestaRepositorio;

@Service
@Transactional
public class RespuestaServicioImpl implements RespuestaServicio {

	private ModelMapper mapper = new ModelMapper();
	
	@Autowired
	RespuestaRepositorio respuestaRepositorio;

	private Respuesta traductorDeDTOaDAO(RespuestaDTO respuesta) {
		try {
			return mapper.map(respuesta, Respuesta.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir un objeto DTO a DAO", e);
		}
	}

	private RespuestaDTO traductorDeDAOaDTO(Respuesta pregunta) {
		try {
			return mapper.map(pregunta, RespuestaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir un objeto DAO a DTO", e);
		}
	}
	
	private List<RespuestaDTO> traductorDeDAOaDTOLista(List<Respuesta> respuestas) {
		try {
			return respuestas.stream().map(respuesta -> mapper.map(respuesta, RespuestaDTO.class))
					.collect(Collectors.toList());
		}catch (Exception e) {
			throw new ServiceException("Error en convertir una lista DAO a DTO", e);
		}
	}
	
	@Override
	public void guardar(RespuestaDTO respuesta) {
		try {
			Respuesta persistente = traductorDeDTOaDAO(respuesta);
			getRespuestaRepositorio().save(persistente);
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la respuesta", e);
		}
	}

	@Override
	public RespuestaDTO obtener(Long id) {
		try {
			return traductorDeDAOaDTO(getRespuestaRepositorio().getReferenceById(id));
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la respuesta", e);
		}
	}

	@Override
	public void borrar(Long id) {
		try {
			getRespuestaRepositorio().deleteById(id);
		} catch (Exception e) {
			throw new ServiceException("Error al borrar la pregunta", e);
		}
	}

	@Override
	public List<RespuestaDTO> listar() {
		return traductorDeDAOaDTOLista(getRespuestaRepositorio().findAll());
	}
	
	public RespuestaRepositorio getRespuestaRepositorio() {
		return respuestaRepositorio;
	}

	public void setRespuestaRepositorio(RespuestaRepositorio respuestaRepositorio) {
		this.respuestaRepositorio = respuestaRepositorio;
	}
}
