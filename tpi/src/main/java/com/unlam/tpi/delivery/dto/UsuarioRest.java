package com.unlam.tpi.delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioRest {

    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("apellido")
    private String apellido;
    @JsonProperty("nombre_usuario")
    private String nombreUsuario;
    @JsonProperty("email")
    private String email;
    @JsonProperty("contraseña")
    private String pass;


	public UsuarioRest(String nombre, String apellido, String nombreUsuario, String email, String pass) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.pass = pass;
	}

	public UsuarioRest() {}
	
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

}