package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.UsuarioLogin;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface LoginServicio {
    String IniciarSesion(UsuarioLogin usuarioLogin) throws NoSuchAlgorithmException, InvalidKeySpecException;

	String IniciarSesionUsuario(UsuarioLogin usuarioLogin) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
