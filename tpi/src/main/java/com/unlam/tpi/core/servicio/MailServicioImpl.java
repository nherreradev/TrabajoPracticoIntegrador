package com.unlam.tpi.core.servicio;

import com.unlam.tpi.delivery.dto.UsuarioRestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.MailServicio;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class MailServicioImpl implements MailServicio{
    private static String EMAIL_MERCADOJR = "grupo.4.tpi.unlam@gmail.com";
    @Autowired
    AutenticacionService autenticacionService;
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void PrepararMailYEnviar(UsuarioRestDTO usuarioRestDTO, String token) {
        String asunto = "Bienvenido a MercadoJR";
        String saludo = "Hola " + usuarioRestDTO.getNombre() + ",";
        String mensaje = "¡Gracias por registrarte en MercadoJR! Para completar el proceso de registro, por favor haz clic en el siguiente enlace:";
        String validacionUrl = "https://tuaplicacion.com/validar-cuenta?token=" + token;
        String cuerpoMail = saludo + "\n\n" + mensaje + "\n" + validacionUrl;
        EnviarMail(usuarioRestDTO.getEmail(), asunto, cuerpoMail);
    }

    @Override
    public void EnviarMail(String destinatario, String asunto, String CuerpoMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EMAIL_MERCADOJR);
        message.setTo(destinatario);
        message.setSubject(asunto);
        message.setText(CuerpoMail);
        emailSender.send(message);

    }
}
