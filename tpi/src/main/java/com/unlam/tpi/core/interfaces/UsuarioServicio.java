package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.UsuarioDTO;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface UsuarioServicio {

	void GuardarUsuario(UsuarioRestDTO usuarioRestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException;
	void ConfirmarCuenta(Usuario usuario);
	Boolean ExisteUsuario(String email);
	Usuario ObtenerUsuarioPorEmail(String email);
	ResponseAPI ModificarUsuario(Usuario usuario);
	ResponseAPI DarDeBajaUsuario(Usuario usuario);
	public Usuario ObtenerUsuarioPorNombreUsuario(String nombreUsuario);
	public UsuarioDTO ObtenerUsuarioDTOPorNombreUsuario(String nombreUsuario);
}
