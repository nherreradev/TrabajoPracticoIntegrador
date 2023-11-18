package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.delivery.dto.TokenDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface LoginServicio {

	public TokenDTO IniciarSesion(UsuarioLogin usuarioLogin) throws NoSuchAlgorithmException, InvalidKeySpecException;

}
