package com.unlam.tpi.core.interfaces;

import javax.transaction.Transactional;

import com.unlam.tpi.core.modelo.UsuarioLogin;

@Transactional
public interface LoginServicio {

	public String iniciarSesion(UsuarioLogin usuarioLogin);

}
