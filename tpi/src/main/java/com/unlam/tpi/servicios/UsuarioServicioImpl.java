package com.unlam.tpi.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Override
	public void guardarUsuario(String nombreUsuario) {
		getUsuarioRepositorio().guardarUsuario(nombreUsuario);
	}

	public UsuarioRepositorio getUsuarioRepositorio() {
		return usuarioRepositorio;
	}

	public void setUsuarioRepositorio(UsuarioRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}

}
