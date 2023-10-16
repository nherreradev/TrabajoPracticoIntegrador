package com.unlam.tpi.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.modelo.persistente.Pregunta;

public class PreguntaDTO {

	private static ModelMapper mapper = new ModelMapper();
	
	private Long oid;
	
	private Integer version;
	
	private Boolean deleted = false;
	
	private String enunciado;
	
	private String descripcion;
	
	private CategoriaDTO categoria;
	
	private SeccionDTO seccion;
	
	private Integer orden;
	
	private TipoComponente tipoComponente;
	
	private List<RespuestaDTO> respuestas;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

	public SeccionDTO getSeccion() {
		return seccion;
	}

	public void setSeccion(SeccionDTO seccion) {
		this.seccion = seccion;
	}

	public List<RespuestaDTO> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<RespuestaDTO> respuestas) {
		this.respuestas = respuestas;
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

	public TipoComponente getTipoComponente() {
		return tipoComponente;
	}

	public void setTipoComponente(TipoComponente tipoComponente) {
		this.tipoComponente = tipoComponente;
	}
	
	public static Pregunta dTOaEntidad(PreguntaDTO pregunta) {
		try {
			return mapper.map(pregunta, Pregunta.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir PreguntaDTO a Pregunta", e);
		}
	}
	
	public static PreguntaDTO entidadADTO(Pregunta pregunta) {
		try {
			return mapper.map(pregunta, PreguntaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir Pregunta a PreguntaDTO", e);
		}
	}
	
	public static List<PreguntaDTO> entidadDTOLista(List<Pregunta> preguntas) {
		try {
			return preguntas.stream().map(pregunta -> mapper.map(pregunta, PreguntaDTO.class))
				.collect(Collectors.toList());
		}catch (Exception e) {
			throw new ServiceException("Error en convertir una lista Pregunta a lista PreguntaDTO", e);
		}
	}
	
	public static List<Pregunta> traductorDeListaDTOaEntidad(List<PreguntaDTO> preguntas) throws ServiceException {
		try {
			return preguntas.stream().map(pregunta -> mapper.map(pregunta, Pregunta.class))
					.collect(Collectors.toList());

		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista PreguntaDTO a lista Pregunta", e);
		}
	}

}