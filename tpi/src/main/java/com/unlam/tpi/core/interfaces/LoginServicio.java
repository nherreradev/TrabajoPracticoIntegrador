package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.delivery.dto.TokenDTO;

public interface LoginServicio {

	public TokenDTO IniciarSesion(UsuarioLogin usuarioLogin);

}
