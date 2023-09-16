package com.unlam.tpi.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.servicio.UsuarioServicio;

@RestController
@RequestMapping("/api")
public class UsuarioControladorImpl implements UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;

	@Override
	@GetMapping("/index")
	public String bienvenido() {
		return "¡Bienvenido al índice de la API!";
	}

	@Override
	@GetMapping("/guardarUsuario")
	public String guardarUsuario(String nombreUsuario) {
		getUsuarioServicio().guardarUsuario(nombreUsuario);
		return "Usuario guardado con exito";
	}

	public UsuarioServicio getUsuarioServicio() {
		return usuarioServicio;
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}

}
