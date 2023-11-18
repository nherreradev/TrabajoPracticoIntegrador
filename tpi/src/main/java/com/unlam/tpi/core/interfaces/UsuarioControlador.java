package com.unlam.tpi.core.interfaces;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.PasswordDto;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;

public interface UsuarioControlador {
	public String bienvenido();
	public ResponseEntity<ResponseAPI> RegistrarUsuario(UsuarioRestDTO usuarioRestDTO) throws Exception;
	public ResponseEntity<ResponseAPI> ModificarUsuario(Usuario usuario);
	ResponseEntity<Usuario> ObtenerDatosUsuarioPorEmail(String email);
	ResponseEntity<ResponseAPI> DarUsuarioDeBaja(Usuario usuario);
	ResponseEntity<ResponseAPI> ActivarCuenta(String token) throws JsonProcessingException;
	ResponseEntity<ResponseAPI> RecuperarCuenta(Usuario usuario) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException;
	ResponseEntity<ResponseAPI> cambiarPassword(PasswordDto passwordDto) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException;
}
