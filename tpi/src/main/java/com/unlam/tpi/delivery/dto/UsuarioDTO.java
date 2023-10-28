package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.infraestructura.arquitectura.ServiceException;

public class UsuarioDTO {

	private static ModelMapper mapper = new ModelMapper();
	
	private Long oid;
	private Integer version;
	private Boolean deleted = false;
	private String nombreUsuario;
	private String nombre;
	private String apellido;
	private String email;
	private String pass;
	private Boolean cuentaConfirmada;
	private Boolean activo;

	public UsuarioDTO(){}

	public UsuarioDTO(String nombreUsuario, String nombre, String apellido, String email, String pass, Boolean cuentaConfirmada, Boolean activo ){
		this.nombreUsuario = nombreUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.pass = pass;
		this.cuentaConfirmada = cuentaConfirmada;
		this.activo = activo;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Boolean getCuentaConfirmada() {
		return cuentaConfirmada;
	}

	public void setCuentaConfirmada(Boolean cuentaConfirmada) {
		this.cuentaConfirmada = cuentaConfirmada;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	public static Usuario dTOaEntidad(UsuarioDTO usuario) {
		try {
			return mapper.map(usuario, Usuario.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir UsuarioDTO a usuario", e);
		}
	}
	
	public static UsuarioDTO entidadADTO(Usuario usuario) {
		try {
			return mapper.map(usuario, UsuarioDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir usuario a UsuarioDTO", e);
		}
	}
	
	public static List<CategoriaDTO> entidadDTOLista(List<UsuarioDTO> usuarios) {
		try {
			return usuarios.stream().map(usuario -> mapper.map(usuario, CategoriaDTO.class))
				.collect(Collectors.toList());
		}catch (Exception e) {
			throw new ServiceException("Error en convertir una lista usuario a lista UsuarioDTO", e);
		}
	}

}
