package com.unlam.tpi.delivery.dto;

public class JWTRestDTO {
    private String accion;
    private String emailUsuario;
    private Boolean estaValidado;
    private String pass;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public Boolean getEstaValidado() {
        return estaValidado;
    }

    public void setEstaValidado(Boolean estaValidado) {
        this.estaValidado = estaValidado;
    }

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
    
}
