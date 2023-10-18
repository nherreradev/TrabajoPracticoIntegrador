package com.unlam.tpi.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.modelo.persistente.Seccion;

public class SeccionDTO {

	private static ModelMapper mapper = new ModelMapper();
	
	private Long oid;
	
	private Integer version;
	
	private Boolean deleted = false;
	
	private String nombre;
	
	private String descripcion;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	public static Seccion dTOaEntidad(SeccionDTO seccion) {
		try {
			return mapper.map(seccion, Seccion.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir SeccionDTO a Seccion", e);
		}
	}
	
	public static SeccionDTO entidadADTO(Seccion seccion) {
		try {
			return mapper.map(seccion, SeccionDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir Seccion a SeccionDTO", e);
		}
	}
	
	public static List<SeccionDTO> entidadDTOLista(List<Seccion> secciones) {
		try {
			return secciones.stream().map(seccion -> mapper.map(seccion, SeccionDTO.class))
				.collect(Collectors.toList());
		}catch (Exception e) {
			throw new ServiceException("Error en convertir una lista Seccion a lista SeccionDTO", e);
		}
	}
	
	public static List<Seccion> traductorDeListaDTOaEntidad(List<SeccionDTO> secciones) throws ServiceException {
		try {
			return secciones.stream().map(seccion -> mapper.map(seccion, Seccion.class))
					.collect(Collectors.toList());

		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista SeccionDTO a lista Seccion", e);
		}
	}
	
}