package com.unlam.tpi.core.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.JWTRestDTO;
import com.unlam.tpi.delivery.dto.UsuarioDTO;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AutenticacionService {
    String GenerarTokenValidacionCuenta(UsuarioRestDTO usuarioRestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException;
    String GenerarTokenLoginUsuario(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException;
    JWTRestDTO ObtenerClaimsToken(String token) throws JsonProcessingException;
    UsuarioDTO obtenerDatosUsuarioByToken(String token) throws JsonProcessingException;
