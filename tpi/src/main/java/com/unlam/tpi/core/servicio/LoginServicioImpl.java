package com.unlam.tpi.core.servicio;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.interfaces.LoginServicio;
import com.unlam.tpi.core.interfaces.UsuarioRepositorio;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.delivery.dto.TokenDTO;

@Service
public class LoginServicioImpl implements LoginServicio {
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    AutenticacionService autenticacionService;
	

    @Override
    public TokenDTO IniciarSesion(UsuarioLogin usuarioLogin) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return getTokenLoginUsuario(usuarioLogin);
    }


	private TokenDTO getTokenLoginUsuario(UsuarioLogin usuarioLogin)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		Usuario usaurio = usuarioRepositorio.findByEmailAndPass(usuarioLogin.getEmail(), usuarioLogin.getPass());
		if (usaurio == null) {
			throw new ServiceException("Usuario/Password invalidos ");
		}
		String token = this.autenticacionService.generarTokenLoginUsuario(usaurio);
		TokenDTO tokenDTO = new TokenDTO(token);
		return tokenDTO;
	}

}
