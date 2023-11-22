package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.UsuarioLogin;

public interface LoginAuthentication {

	public String iniciarSesion(UsuarioLogin usuarioLogin);
	
	public String getTokenLoginUsuario(UsuarioLogin usuarioLogin);
}
