package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.JWTRest;
import com.unlam.tpi.core.modelo.Usuario;

public interface AutenticacionService {

    String generarTokenLoginUsuario(Usuario usuario);

    JWTRest obtenerClaimsToken(String token);

    Usuario obtenerDatosUsuarioByToken(String token);

	String GenerarTokenValidacionCuenta(String email);

	String GenerarSecretJWT();

}
