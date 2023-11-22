package com.unlam.tpi.core.interfaces;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.Usuario;

@Transactional
public interface UsuarioServicio {

	void guardarUsuario(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException;

	void confirmarCuenta(Usuario usuario);

	Boolean existeEmail(String email);

	Boolean existeNombreUsuario(String nombreUsuario);

	Usuario obtenerUsuarioPorEmail(String email);

	ResponseAPI modificarUsuario(Usuario usuario);

	ResponseAPI DarDeBajaUsuario(Usuario usuario);

	public Usuario obtenerUsuarioPorNombreUsuario(String nombreUsuario);

	boolean usuarioValidadoPorPrimeraVez(String token) throws JsonProcessingException;

	Boolean elUsuarioFueYaEstaValidado(String token) throws JsonProcessingException;

	void recuperarCuenta(Usuario usuario);

	Usuario obtenerUsuarioPorToken(String token);

	public Boolean cambioPassword(String newPassword, String token);

	List<Usuario> obtenerTodosLosUsuarios();
}
