package com.unlam.tpi.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.dto.SeccionDTO;
import com.unlam.tpi.modelo.persistente.Seccion;
import com.unlam.tpi.repositorio.SeccionRepositorio;

@Service
public class SeccionServicioImpl implements SeccionServicio {

	@Autowired
	SeccionRepositorio seccionRepositorio;

	private ModelMapper mapper = new ModelMapper();

	private Seccion traductorDeDTOaDAO(SeccionDTO Seccion) {
		try {
			return mapper.map(Seccion, Seccion.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir un objeto DTO a DAO", e);
		}
	}

	private SeccionDTO traductorDeDAOaDTO(Seccion Seccion) {
		try {
			return mapper.map(Seccion, SeccionDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir un objeto DAO a DTO", e);
		}
	}

	private List<SeccionDTO> traductorDeDAOaDTOLista(List<Seccion> Seccions) {
		try {
			return Seccions.stream().map(Seccion -> mapper.map(Seccion, SeccionDTO.class)).collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista DAO a DTO", e);
		}
	}

	@Override
	public void guardar(SeccionDTO Seccion) {
		try {
			Seccion persistente = traductorDeDTOaDAO(Seccion);
			getSeccionRepositorio().save(persistente);
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la pregunta", e);
		}
	}

	@Override
	public SeccionDTO obtener(Long id) {
		try {
			return traductorDeDAOaDTO(getSeccionRepositorio().getReferenceById(id));
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
		return traductorDeDAOaDTOLista(getSeccionRepositorio().findAll());
	}

	public SeccionRepositorio getSeccionRepositorio() {
		return seccionRepositorio;
	}

	public void setSeccionRepositorio(SeccionRepositorio seccionRepositorio) {
		this.seccionRepositorio = seccionRepositorio;
	}

}
