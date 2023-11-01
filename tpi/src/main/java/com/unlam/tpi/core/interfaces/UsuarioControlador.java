package com.unlam.tpi.core.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;
import org.springframework.http.ResponseEntity;

import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.Usuario;

public interface UsuarioControlador {
	public String bienvenido();
	public ResponseEntity<ResponseAPI> RegistrarUsuario(UsuarioRestDTO usuarioRestDTO) throws Exception;
	public ResponseEntity<ResponseAPI> ModificarUsuario(Usuario usuario);
	ResponseEntity<Usuario> ObtenerDatosUsuarioPorEmail(String email);
	ResponseEntity<ResponseAPI> DarUsuarioDeBaja(Usuario usuario);
	ResponseEntity<ResponseAPI> ActivarCuenta(String token) throws JsonProcessingException;
}
