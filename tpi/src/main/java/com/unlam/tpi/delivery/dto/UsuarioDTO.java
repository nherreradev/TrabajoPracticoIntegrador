package com.unlam.tpi.delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioDTO {

	@JsonProperty("oid")
	private Long oid;
	@JsonProperty("version")
	private Integer version;
	@JsonProperty("deleted")
	private Boolean deleted = false;
	@JsonProperty("username")
	private String nombreUsuario;
	@JsonProperty("nombre")
	private String nombre;
	@JsonProperty("apellido")
	private String apellido;
	@JsonProperty("email")
	private String email;
	@JsonProperty("pass")
	private String pass;
	@JsonProperty("cuentaConfirmada")
	private Boolean cuentaConfirmada;
	@JsonProperty("activo")
	private Boolean activo;
	@JsonProperty("premium")
	private Boolean premium;
	@JsonProperty("esAdministrador")
	private Boolean esAdministrador;
	
	public UsuarioDTO() {
	}

	public UsuarioDTO(String nombreUsuario, String nombre, String apellido, String email, String pass,
			Boolean cuentaConfirmada, Boolean activo, Boolean esAdministrador) {
		this.nombreUsuario = nombreUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.pass = pass;
		this.cuentaConfirmada = cuentaConfirmada;
		this.activo = activo;
		this.esAdministrador = esAdministrador;
	}

	@JsonProperty("username")
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	@JsonProperty("oid")
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	@JsonProperty("version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@JsonProperty("deleted")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@JsonProperty("apellido")
	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("pass")
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@JsonProperty("cuentaConfirmada")
	public Boolean getCuentaConfirmada() {
		return cuentaConfirmada;
	}

	public void setCuentaConfirmada(Boolean cuentaConfirmada) {
		this.cuentaConfirmada = cuentaConfirmada;
	}

	@JsonProperty("activo")
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@JsonProperty("premium")
	public Boolean getPremium() {
		return premium;
	}

	public void setPremium(Boolean premium) {
		this.premium = premium;
	}

	@JsonProperty("esAdministrador")
	public Boolean getEsAdministrador() {
		return esAdministrador;
	}

	public void setEsAdministrador(Boolean esAdministrador) {
		this.esAdministrador = esAdministrador;
	}
	
}