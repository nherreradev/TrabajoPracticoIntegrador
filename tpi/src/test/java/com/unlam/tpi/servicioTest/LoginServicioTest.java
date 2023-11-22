package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.interfaces.LoginAuthentication;
import com.unlam.tpi.core.interfaces.UsuarioRepositorio;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.core.servicio.LoginServicioImpl;
import com.unlam.tpi.delivery.dto.UsuarioDTO;

@ExtendWith(MockitoExtension.class)
public class LoginServicioTest {
	@Mock
	private UsuarioRepositorio usuarioRepositorio;

	@Mock
	private AutenticacionService autenticacionService;

	@Mock
	private LoginAuthentication loginAuthentication;

	@InjectMocks
	private LoginServicioImpl loginServicio;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void iniciarSesion() throws NoSuchAlgorithmException, InvalidKeySpecException {

		UsuarioLogin usuarioLogin = new UsuarioLogin();
		usuarioLogin.setEmail("usuariotest@dominio.com");
		usuarioLogin.setPass("123456ASD");

		Usuario usuario = new Usuario();
		usuario.setEmail("usuariotest@dominio.com");
		usuario.setPass("123456ASD");

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setEmail("usuariotest@dominio.com");
		usuarioDTO.setPass("123456ASD");

		when(loginAuthentication.iniciarSesion(usuarioLogin)).thenReturn("testToken");

		String resultToken = loginServicio.iniciarSesion(usuarioLogin);

		assertEquals("testToken", resultToken);
	}

}