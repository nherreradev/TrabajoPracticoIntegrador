package com.unlam.tpi.servicios;

import org.springframework.transaction.annotation.Transactional;

public interface UsuarioServicio {

	@Transactional
	public void guardarUsuario(String nombreUsuario);
}
