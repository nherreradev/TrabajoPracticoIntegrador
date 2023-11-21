package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.unlam.tpi.core.interfaces.UsuarioRepositorio;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.core.servicio.LoginServicioImpl;
import com.unlam.tpi.delivery.dto.TokenDTO;
import com.unlam.tpi.delivery.dto.UsuarioDTO;

@ExtendWith(MockitoExtension.class)
public class LoginServicioTest {
	@Mock
	private UsuarioRepositorio usuarioRepositorio;

	@Mock
	private AutenticacionService autenticacionService;

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
		usuario.setEmail("usuariotest@dominio.com");
		usuario.setPass("123456ASD");

		TokenDTO tokenDTO = new TokenDTO("testToken");

		when(usuarioRepositorio.findByEmailAndPass("usuariotest@dominio.com", "123456ASD")).thenReturn(usuario);

		when(autenticacionService.generarTokenLoginUsuario(usuarioDTO)).thenReturn("testToken");

		TokenDTO resultTokenDTO = loginServicio.IniciarSesion(usuarioLogin);

		verify(usuarioRepositorio, times(1)).findByEmailAndPass("usuariotest@dominio.com", "123456ASD");
		verify(autenticacionService, times(1)).generarTokenLoginUsuario(usuarioDTO);

		assertEquals(tokenDTO.getToken(), resultTokenDTO.getToken());
	}

	@Test
	public void iniciarSesionUsuarioNoEncontrado() throws NoSuchAlgorithmException, InvalidKeySpecException {

		UsuarioLogin usuarioLogin = new UsuarioLogin();
		usuarioLogin.setEmail("usuariotest@dominio.com");
		usuarioLogin.setPass("123456ASD");

		when(usuarioRepositorio.findByEmailAndPass("usuariotest@dominio.com", "123456ASD")).thenReturn(null);

		ServiceException exception = assertThrows(ServiceException.class,
				() -> loginServicio.IniciarSesion(usuarioLogin));

		assertEquals("Usuario/Password invalidos ", exception.getMessage());

		verify(usuarioRepositorio, times(1)).findByEmailAndPass("usuariotest@dominio.com", "123456ASD");
		verify(autenticacionService, never()).generarTokenLoginUsuario(any());
	}
}
