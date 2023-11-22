package com.unlam.tpi.core.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.LoginAuthentication;
import com.unlam.tpi.core.interfaces.LoginServicio;
import com.unlam.tpi.core.modelo.UsuarioLogin;

@Service
public class LoginServicioImpl implements LoginServicio {

	@Autowired
	LoginAuthentication loginAuthentication;

	@Override
	public String iniciarSesion(UsuarioLogin usuarioLogin) {
		return loginAuthentication.iniciarSesion(usuarioLogin);
	}

}
