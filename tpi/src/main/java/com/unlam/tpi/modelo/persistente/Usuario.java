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

	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

}
