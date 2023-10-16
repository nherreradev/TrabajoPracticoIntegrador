package com.unlam.tpi.servicio;

import org.springframework.transaction.annotation.Transactional;

public interface UsuarioServicio {

	@Transactional
	public void guardarUsuario(String nombreUsuario);
}
