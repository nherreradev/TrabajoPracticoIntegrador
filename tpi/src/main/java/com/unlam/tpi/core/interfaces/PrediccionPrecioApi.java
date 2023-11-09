package com.unlam.tpi.core.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.delivery.dto.JWTRestDTO;
import com.unlam.tpi.delivery.dto.PrediccionPrecioDTO;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface PrediccionPrecioApi {

	public PrediccionPrecioDTO obtenerPrecio();

    interface AutenticacionService {
        String GenerarTokenValidacionCuenta(UsuarioRestDTO usuarioRestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException;
        JWTRestDTO ObtenerClaimsToken(String token) throws JsonProcessingException;

    }
}
