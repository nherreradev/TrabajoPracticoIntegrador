package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.Respuesta;
import com.unlam.tpi.core.modelo.ServiceException;

public class RespuestaDTO {

	private static ModelMapper mapper = new ModelMapper();

	private Long oid;

	private Integer version;

	private Boolean deleted = false;

	private String instrumento;

	private String nombre;

	private Integer valor;

	private Integer orden;

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

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
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

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	public static Respuesta dTOaEntidad(RespuestaDTO respuesta) {
		try {
			return mapper.map(respuesta, Respuesta.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir RespuestaDTO a Respuesta", e);
		}
	}

	public static RespuestaDTO entidadADTO(Respuesta respuesta) {
		try {
			return mapper.map(respuesta, RespuestaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir Respuesta a RespuestaDTO", e);
		}
	}

	public static List<RespuestaDTO> entidadDTOLista(List<Respuesta> respuestas) {
		try {
			return respuestas.stream().map(respuesta -> mapper.map(respuesta, RespuestaDTO.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista Respuesta a lista RespuestaDTO", e);
		}
	}

	public static List<Respuesta> traductorDeListaDTOaEntidad(List<RespuestaDTO> respuestas) throws ServiceException {
		try {
			return respuestas.stream().map(respuesta -> mapper.map(respuesta, Respuesta.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista RespuestaDTO a lista Respuesta", e);
		}
	}

}