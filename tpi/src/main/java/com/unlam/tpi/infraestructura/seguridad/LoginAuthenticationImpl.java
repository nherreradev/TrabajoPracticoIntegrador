package com.unlam.tpi.infraestructura.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.interfaces.LoginAuthentication;
import com.unlam.tpi.core.interfaces.UsuarioRepositorio;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.delivery.dto.TokenDTO;
import com.unlam.tpi.delivery.dto.UsuarioMapper;

@Service
public class LoginAuthenticationImpl implements LoginAuthentication{

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Autowired
	AutenticacionService autenticacionService;

	@Override
	public TokenDTO iniciarSesion(UsuarioLogin usuarioLogin) {
		return getTokenLoginUsuario(usuarioLogin);
	}

	@Override
	public TokenDTO getTokenLoginUsuario(UsuarioLogin usuarioLogin) {
		Usuario usuario = usuarioRepositorio.findByEmailAndPass(usuarioLogin.getEmail(), usuarioLogin.getPass());
		if (usuario == null) {
			throw new ServiceException("Usuario/Password invalidos ");
		}
		String token = this.autenticacionService.generarTokenLoginUsuario(UsuarioMapper.entidadADTO(usuario));
		TokenDTO tokenDTO = new TokenDTO(token);
		return tokenDTO;
	}

}
