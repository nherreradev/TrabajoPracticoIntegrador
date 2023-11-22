package com.unlam.tpi.infraestructura.api;

import com.unlam.tpi.core.interfaces.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.MailServicio;
import com.unlam.tpi.core.modelo.Usuario;

@Service
public class MailServicioImpl implements MailServicio {

	private static String EMAIL_MERCADOJR = "grupo.4.tpi.unlam@gmail.com";

	@Autowired
	AutenticacionService autenticacionService;

	@Autowired
	private JavaMailSender emailSender;

	@Value("${mercado.jr.url}")
	private String urlMercadoJr;

	@Override
	public void prepararMailYEnviar(Usuario usuario, String token) {
		String asunto = "Bienvenido a MercadoJR";
		String saludo = "Hola " + usuario.getNombre() + ",";
		String mensaje = "¡Gracias por registrarte en MercadoJR! Para completar el proceso de registro, active su cuenta con el siguiente token:";
		String validacionUrl = urlMercadoJr + "activar-cuenta?token=" + token;
		String cuerpoMail = saludo + "\n\n" + mensaje + "\n\n" + validacionUrl + "\n\n";
		enviarMail(usuario.getEmail(), asunto, cuerpoMail);
	}

	@Override
	public void envioMailRecuperacionCuenta(String nombre, String email, String token) {
		String asunto = "Bienvenido a MercadoJR";
		String saludo = "Hola " + nombre + ",";
		String mensaje = "Para completar el proceso de recuperacion de cuenta dirigirse al siguiente link:";
		String validacionUrl = urlMercadoJr + "recuperar-cuenta?token=" + token;
		String cuerpoMail = saludo + "\n\n" + mensaje + "\n\n" + validacionUrl + "\n\n";
		enviarMail(email, asunto, cuerpoMail);
	}

	@Override
	public void enviarMail(String destinatario, String asunto, String CuerpoMail) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(EMAIL_MERCADOJR);
		message.setTo(destinatario);
		message.setSubject(asunto);
		message.setText(CuerpoMail);
		emailSender.send(message);

	}
}
