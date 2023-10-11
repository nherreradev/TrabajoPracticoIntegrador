package com.unlam.tpi.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.SeccionDTO;
import com.unlam.tpi.helpers.TraductorGenerico;
import com.unlam.tpi.modelo.persistente.Seccion;
import com.unlam.tpi.repositorio.SeccionRepositorio;

@Service
public class SeccionServicioImpl implements SeccionServicio {
	@Autowired
	SeccionRepositorio seccionRepositorio;

	@Override
	public void guardar(SeccionDTO seccion) {
		try {
			Seccion persistente = TraductorGenerico.traductorDeDTOaEntidad(seccion, Seccion.class);
			getSeccionRepositorio().save(persistente);
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la pregunta", e);
		}
	}

	@Override
	public SeccionDTO obtener(Long id) {
		try {
			return TraductorGenerico.traductorDeEntidadaDTO(getSeccionRepositorio().getReferenceById(id), SeccionDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la pregunta", e);
		}
	}

	@Override
	public void borrar(Long id) {
		try {
			getSeccionRepositorio().deleteById(id);
		} catch (Exception e) {
			throw new ServiceException("Error al borrar la pregunta", e);
		}
	}

	@Override
	public List<SeccionDTO> listar() {
		return TraductorGenerico.traductorDeListaEntidadaDTO(getSeccionRepositorio().findAll(), SeccionDTO.class);
	}

	public SeccionRepositorio getSeccionRepositorio() {
		return seccionRepositorio;
	}

	public void setSeccionRepositorio(SeccionRepositorio seccionRepositorio) {
		this.seccionRepositorio = seccionRepositorio;
	}

}
