package com.unlam.tpi.modelo.persistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.unlam.tpi.arquitectura.ObjetoPersistente;

@Entity
@Table(name = "usuario")
public class Usuario extends ObjetoPersistente{

	private static final long serialVersionUID = 1L;

	@Column(name = "NOMBRE_USUARIO")
	private String nombreUsuario;
	@Column(name = "NOMBRE")
	private String nombre;
	@Column(name = "APELLIDO")
	private String apellido;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "PASSWORD")
	private String pass;
	@Column(name = "CUENTA_CONFIRMADA")
	private Boolean cuentaConfirmada;
	@Column(name = "ESTA_ACTIVO")
	private Boolean activo;

	public Usuario(){}

	public Usuario(String nombreUsuario, String nombre, String apellido, String email, String pass, Boolean cuentaConfirmada, Boolean activo ){
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
}
