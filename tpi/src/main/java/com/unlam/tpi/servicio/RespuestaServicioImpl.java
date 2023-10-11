package com.unlam.tpi.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.RespuestaDTO;
import com.unlam.tpi.helpers.TraductorGenerico;
import com.unlam.tpi.modelo.persistente.Respuesta;
import com.unlam.tpi.repositorio.RespuestaRepositorio;

@Service
public class RespuestaServicioImpl implements RespuestaServicio {

	@Autowired
	RespuestaRepositorio respuestaRepositorio;

	
	@Override
	public void guardar(RespuestaDTO respuesta) {
		try {
			Respuesta persistente = TraductorGenerico.traductorDeDTOaEntidad(respuesta, Respuesta.class);
			getRespuestaRepositorio().save(persistente);
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la respuesta", e);
		}
	}

	@Override
	public RespuestaDTO obtener(Long id) {
		try {
			return TraductorGenerico.traductorDeEntidadaDTO(getRespuestaRepositorio().getReferenceById(id), RespuestaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la respuesta", e);
		}
	}

	@Override
	public void borrar(Long id) {
		try {
			getRespuestaRepositorio().deleteById(id);
		} catch (Exception e) {
			throw new ServiceException("Error al borrar la respuesta", e);
		}
	}

	@Override
	public List<RespuestaDTO> listar() {
		try {
			return TraductorGenerico.traductorDeListaEntidadaDTO(getRespuestaRepositorio().findAll(), RespuestaDTO.class);
		} catch (ServiceException e) {
			throw new ServiceException("Error al listar las respuestas", e);
		}
	}
	
	public RespuestaRepositorio getRespuestaRepositorio() {
		return respuestaRepositorio;
	}

	public void setRespuestaRepositorio(RespuestaRepositorio respuestaRepositorio) {
		this.respuestaRepositorio = respuestaRepositorio;
	}
}
