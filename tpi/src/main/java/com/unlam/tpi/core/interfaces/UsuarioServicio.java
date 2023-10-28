package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.delivery.dto.UsuarioDTO;
import com.unlam.tpi.infraestructura.modelo.ResponseAPI;
import com.unlam.tpi.infraestructura.modelo.Usuario;

import org.springframework.transaction.annotation.Transactional;

public interface UsuarioServicio {

	void GuardarUsuario(Usuario usuario) throws Exception;
	void ConfirmarCuenta(Usuario usuario);
	Boolean ExisteUsuario(Usuario usuario);
	Usuario ObtenerUsuarioPorEmail(String email);
	ResponseAPI ModificarUsuario(Usuario usuario);
	ResponseAPI DarDeBajaUsuario(Usuario usuario);
	public Usuario ObtenerUsuarioPorNombreUsuario(String nombreUsuario);
	public UsuarioDTO ObtenerUsuarioDTOPorNombreUsuario(String nombreUsuario);
}
