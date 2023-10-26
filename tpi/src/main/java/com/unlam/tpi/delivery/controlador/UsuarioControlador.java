package com.unlam.tpi.delivery.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unlam.tpi.infraestructura.modelo.ResponseAPI;
import com.unlam.tpi.infraestructura.modelo.Usuario;

public interface UsuarioControlador {
	public String bienvenido();
	public ResponseEntity<ResponseAPI> RegistrarUsuario(Usuario usuario) throws Exception;
	public ResponseEntity<ResponseAPI> ModificarUsuario(Usuario usuario);
	ResponseEntity<Usuario> ObtenerDatosUsuarioPorEmail(String email);
	ResponseEntity<ResponseAPI> DarUsuarioDeBaja(Usuario usuario);
}
