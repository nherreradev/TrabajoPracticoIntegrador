package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.interfaces.UsuarioRepositorio;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.UsuarioLogin;
import com.unlam.tpi.infraestructura.seguridad.LoginAuthenticationImpl;

@ExtendWith(MockitoExtension.class)
public class LoginAuthenticationTest {

	@InjectMocks
	private LoginAuthenticationImpl loginAuthentication;
	
	@Mock
	private UsuarioRepositorio usuarioRepositorio;
	
	@Mock
	private AutenticacionService autenticacionService;
	
	@Test
	public void iniciarSesionUsuarioNoEncontrado() throws NoSuchAlgorithmException, InvalidKeySpecException {

	    UsuarioLogin usuarioLogin = new UsuarioLogin();
	    usuarioLogin.setEmail("usuariotest@dominio.com");
	    usuarioLogin.setPass("123456ASD");

	    when(usuarioRepositorio.findByEmailAndPass("usuariotest@dominio.com", "123456ASD")).thenReturn(null);

	    ServiceException exception = assertThrows(ServiceException.class,
	            () -> loginAuthentication.getTokenLoginUsuario(usuarioLogin));

	    assertEquals("Usuario/Password invalidos ", exception.getMessage());
	}


}
