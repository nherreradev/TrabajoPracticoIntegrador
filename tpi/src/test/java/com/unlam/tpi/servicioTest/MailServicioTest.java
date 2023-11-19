package com.unlam.tpi.servicioTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.servicio.MailServicioImpl;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;

@ExtendWith(MockitoExtension.class)
public class MailServicioTest {

	@Mock
	private AutenticacionService autenticacionService;

	@Mock
	private JavaMailSender emailSender;

	@InjectMocks
	private MailServicioImpl mailServicio;

	@Test
	public void prepararMailYEnviar() {

		UsuarioRestDTO usuarioRestDTO = new UsuarioRestDTO();
		usuarioRestDTO.setNombre("usuario");
		usuarioRestDTO.setEmail("usuario@dominio.com");
		String token = "testToken";

		mailServicio.prepararMailYEnviar(usuarioRestDTO, token);

		verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
	}

	@Test
	public void envioMailRecuperacionCuenta() {
		// Configurar datos de prueba
		String nombre = "usuario";
		String email = "usuario@dominio.com\"";
		String token = "testToken";

		mailServicio.envioMailRecuperacionCuenta(nombre, email, token);

		verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
	}

}
