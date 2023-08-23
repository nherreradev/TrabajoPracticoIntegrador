package com.unlam.tpi.servicios;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unlam.tpi.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {
	
	private static final Logger logger = LogManager.getLogger(UsuarioServicioImpl.class);

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Override
	public void guardarUsuario(String nombreUsuario) {
		try {
			getUsuarioRepositorio().guardarUsuario(nombreUsuario);
		} catch (Exception e) {
			logger.error("Error en UsuarioServicioImpl " + e);
		}
		
	}

	public UsuarioRepositorio getUsuarioRepositorio() {
		return usuarioRepositorio;
	}

	public void setUsuarioRepositorio(UsuarioRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}

}
