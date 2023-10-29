package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.Usuario;

public interface UsuarioControlador {
	public String bienvenido();
	public ResponseEntity<ResponseAPI> RegistrarUsuario(Usuario usuario) throws Exception;
	public ResponseEntity<ResponseAPI> ModificarUsuario(Usuario usuario);
	ResponseEntity<Usuario> ObtenerDatosUsuarioPorEmail(String email);
	ResponseEntity<ResponseAPI> DarUsuarioDeBaja(Usuario usuario);
}
