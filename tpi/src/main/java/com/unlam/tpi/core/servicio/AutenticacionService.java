package com.unlam.tpi.core.servicio;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.delivery.dto.JWTRestDTO;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;

public interface AutenticacionService {
    String GenerarTokenValidacionCuenta(UsuarioRestDTO usuarioRestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException;
    JWTRestDTO ObtenerClaimsToken(String token) throws JsonProcessingException;

}
