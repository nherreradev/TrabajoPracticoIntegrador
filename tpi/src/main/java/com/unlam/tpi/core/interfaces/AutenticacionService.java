package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.JWTRestDTO;
import com.unlam.tpi.delivery.dto.UsuarioDTO;

public interface AutenticacionService {

    String generarTokenLoginUsuario(Usuario usuario);

    JWTRestDTO obtenerClaimsToken(String token);

    UsuarioDTO obtenerDatosUsuarioByToken(String token);

	String GenerarTokenValidacionCuenta(String email);

	String GenerarSecretJWT();

}
