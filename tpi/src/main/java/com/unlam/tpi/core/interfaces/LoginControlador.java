package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.UsuarioLogin;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface LoginControlador {
    ResponseEntity<String> IniciarSesion(UsuarioLogin usuarioLogin) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
