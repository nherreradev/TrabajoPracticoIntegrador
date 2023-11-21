package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.JWTRest;
import com.unlam.tpi.delivery.dto.UsuarioDTO;

public interface AutenticacionService {

    String generarTokenLoginUsuario(UsuarioDTO usuario);

    JWTRest obtenerClaimsToken(String token);

    UsuarioDTO obtenerDatosUsuarioByToken(String token);

	String GenerarTokenValidacionCuenta(String email);

	String GenerarSecretJWT();

}
