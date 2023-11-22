package com.unlam.tpi.core.interfaces;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.delivery.dto.TokenDTO;

public interface LoginControlador {
	public ResponseEntity<TokenDTO> IniciarSesion(UsuarioLogin usuarioLogin)
			throws NoSuchAlgorithmException, InvalidKeySpecException;
}
