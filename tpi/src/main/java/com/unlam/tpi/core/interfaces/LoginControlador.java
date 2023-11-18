package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.delivery.dto.TokenDTO;

import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface LoginControlador {
	public ResponseEntity<TokenDTO> IniciarSesion(UsuarioLogin usuarioLogin)
			throws NoSuchAlgorithmException, InvalidKeySpecException;
}
