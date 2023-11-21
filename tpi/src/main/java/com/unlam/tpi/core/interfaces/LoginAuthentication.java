package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.delivery.dto.TokenDTO;

public interface LoginAuthentication {

	public TokenDTO iniciarSesion(UsuarioLogin usuarioLogin);
	
	public TokenDTO getTokenLoginUsuario(UsuarioLogin usuarioLogin);
}
