package com.unlam.tpi.core.interfaces;

import javax.transaction.Transactional;

import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.delivery.dto.TokenDTO;

@Transactional
public interface LoginServicio {

	public TokenDTO IniciarSesion(UsuarioLogin usuarioLogin);

}
