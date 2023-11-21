package com.unlam.tpi.core.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.PasswordDto;
import com.unlam.tpi.delivery.dto.UsuarioDTO;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.transaction.Transactional;

@Transactional
public interface UsuarioServicio {

	void guardarUsuario(UsuarioRestDTO usuarioRestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException;
	void confirmarCuenta(Usuario usuario);
	Boolean existeEmail(String email);
	Boolean existeNombreUsuario(String nombreUsuario);
	Usuario obtenerUsuarioPorEmail(String email);
	ResponseAPI modificarUsuario(Usuario usuario);
	ResponseAPI DarDeBajaUsuario(Usuario usuario);
	public Usuario obtenerUsuarioPorNombreUsuario(String nombreUsuario);
	public UsuarioDTO obtenerUsuarioDTOPorNombreUsuario(String nombreUsuario);
	boolean usuarioValidadoPorPrimeraVez(String token) throws JsonProcessingException;
	Boolean elUsuarioFueYaEstaValidado(String token) throws JsonProcessingException;
	void recuperarCuenta(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException;
	Usuario obtenerUsuarioPorToken(String token);
	Boolean cambioPassword(PasswordDto passwordDto) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException;
	List<Usuario> obtenerTodosLosUsuarios();
}
