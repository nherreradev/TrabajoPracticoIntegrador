package com.unlam.tpi.controlador;

import com.unlam.tpi.modelo.persistente.Usuario;
import com.unlam.tpi.modelo.rest.ResponseAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

public interface UsuarioControlador {
	public String bienvenido();
	public ResponseEntity<ResponseAPI> RegistrarUsuario(Usuario usuario) throws Exception;
	public ResponseEntity<ResponseAPI> ModificarUsuario(Usuario usuario);
	ResponseEntity<Usuario> ObtenerDatosUsuarioPorEmail(String email);
	ResponseEntity<ResponseAPI> DarUsuarioDeBaja(Usuario usuario);
}
