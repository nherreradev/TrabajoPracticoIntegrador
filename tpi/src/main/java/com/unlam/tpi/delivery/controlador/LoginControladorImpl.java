package com.unlam.tpi.delivery.controlador;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.core.interfaces.LoginControlador;
import com.unlam.tpi.core.interfaces.LoginServicio;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.delivery.dto.TokenDTO;

@RestController
@RequestMapping("/login")
public class LoginControladorImpl implements LoginControlador {
	
	@Autowired
	UsuarioServicio usuarioServicio;
	
	@Autowired
	LoginServicio loginServicio;

	@Override
	@PostMapping("/iniciar-sesion")
	public ResponseEntity<TokenDTO> IniciarSesion(@RequestBody UsuarioLogin usuarioLogin)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		if (usuarioLogin == null) {
			return ResponseEntity.badRequest().body(new TokenDTO());
		}
		return ResponseEntity.ok(new TokenDTO(this.loginServicio.iniciarSesion(usuarioLogin)));
	}
	
}