package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.JWTRestDTO;
import com.unlam.tpi.infraestructura.seguridad.AutenticacionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AutenticacionServiceTest {

	@Mock
	private JWTRestDTO jwtRestDTO;

	@InjectMocks
	private AutenticacionServiceImpl autenticacionService;

	@Test
	void generarTokenValidacionCuenta() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String token = autenticacionService.GenerarTokenValidacionCuenta("usuariotest@gmail.com");
		assertEquals(true, token.length() > 0);
	}

	@Test
	void generarTokenLoginUsuario() throws NoSuchAlgorithmException, InvalidKeySpecException {
		Usuario usuario = Mockito.mock(Usuario.class);
		Mockito.lenient().when(usuario.getNombreUsuario()).thenReturn("usuariotest");
		Mockito.lenient().when(usuario.getNombre()).thenReturn("Usuario");
		Mockito.lenient().when(usuario.getApellido()).thenReturn("Test");
		Mockito.lenient().when(usuario.getEmail()).thenReturn("usuariotest@gmail.com");
		Mockito.lenient().when(usuario.getPremium()).thenReturn(true);
		Mockito.lenient().when(usuario.getOid()).thenReturn(123L);
		Mockito.lenient().when(usuario.getPass()).thenReturn("123456");
		Mockito.lenient().when(usuario.getEsAdministrador()).thenReturn(true);

		String token = autenticacionService.generarTokenLoginUsuario(usuario);
		assertEquals(true, token.length() > 0);
	}

}
