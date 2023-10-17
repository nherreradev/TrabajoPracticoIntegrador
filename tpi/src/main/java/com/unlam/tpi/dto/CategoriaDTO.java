package com.unlam.tpi.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.modelo.persistente.Categoria;

public class CategoriaDTO {

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
	
	public static Categoria dTOaEntidad(CategoriaDTO categoria) {
		try {
			return mapper.map(categoria, Categoria.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir CategoriaDTO a Categoria", e);
		}
	}
	
	public static CategoriaDTO entidadADTO(Categoria categoria) {
		try {
			return mapper.map(categoria, CategoriaDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir Categoria a CategoriaDTO", e);
		}
	}
	
	public static List<CategoriaDTO> entidadDTOLista(List<Categoria> categorias) {
		try {
			return categorias.stream().map(categoria -> mapper.map(categoria, CategoriaDTO.class))
				.collect(Collectors.toList());
		}catch (Exception e) {
			throw new ServiceException("Error en convertir una lista Categoria a lista CategoriaDTO", e);
		}
	}
	
	public static List<Categoria> traductorDeListaDTOaEntidad(List<CategoriaDTO> categorias) throws ServiceException {
		try {
			return categorias.stream().map(categoria -> mapper.map(categoria, Categoria.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista CategoriaDTO a lista Categoria", e);
		}
	}
	

}