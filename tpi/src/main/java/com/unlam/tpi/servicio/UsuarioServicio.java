package com.unlam.tpi.servicio;

import com.unlam.tpi.modelo.persistente.Usuario;
import com.unlam.tpi.modelo.rest.ResponseAPI;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioServicio {

	void GuardarUsuario(Usuario usuario) throws Exception;
	void ConfirmarCuenta(Usuario usuario);
	Boolean ExisteUsuario(Usuario usuario);
	Usuario ObtenerUsuarioPorEmail(String email);
	ResponseAPI ModificarUsuario(Usuario usuario);
	ResponseAPI DarDeBajaUsuario(Usuario usuario);
}
