package com.unlam.tpi.core.interfaces;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.JWTRestDTO;
import com.unlam.tpi.delivery.dto.UsuarioDTO;

public interface AutenticacionService {

    String generarTokenLoginUsuario(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException;

    JWTRestDTO obtenerClaimsToken(String token) throws JsonProcessingException;

    UsuarioDTO obtenerDatosUsuarioByToken(String token) throws JsonProcessingException;

	String GenerarTokenValidacionCuenta(String email) throws NoSuchAlgorithmException, InvalidKeySpecException;

	String GenerarSecretJWT() throws NoSuchAlgorithmException, InvalidKeySpecException;

}
