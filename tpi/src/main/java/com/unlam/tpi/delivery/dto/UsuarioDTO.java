package com.unlam.tpi.delivery.dto;

public class UsuarioDTO {

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
	private Boolean premium;
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

	public Boolean getPremium() {
		return premium;
	}

	public void setPremium(Boolean premium) {
		this.premium = premium;
	}

	public Boolean getEsAdministrador() {
		return esAdministrador;
	}

	public void setEsAdministrador(Boolean esAdministrador) {
		this.esAdministrador = esAdministrador;
	}
	
}