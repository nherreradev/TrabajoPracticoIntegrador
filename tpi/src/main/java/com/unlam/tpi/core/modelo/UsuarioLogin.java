package com.unlam.tpi.core.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioLogin {
    @JsonProperty("email")
    public String Mail;
    @JsonProperty("contraseña")
    private String Pass;

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
