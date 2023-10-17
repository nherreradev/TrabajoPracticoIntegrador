package com.unlam.tpi.servicio;

import com.unlam.tpi.modelo.persistente.Usuario;
import org.springframework.stereotype.Service;

@Service
public class MailServicioImpl implements MailServicio{

    @Override
    public void PrepararMail(Usuario usuario) {
        String asunto = "Bienvenido a MercadoJR";
        String saludo = "Hola " + usuario.getNombre() + ",";
        String mensaje = "¡Gracias por registrarte en MercadoJR! Para completar el proceso de registro, por favor haz clic en el siguiente enlace:";
        String validacionUrl = "https://tuaplicacion.com/validar-cuenta?token=" + GetTokenValidator();
        String cuerpoMail = saludo + "\n\n" + mensaje + "\n" + validacionUrl;
        EnviarMail(usuario.getEmail(), asunto, cuerpoMail);
    }

    private String GetTokenValidator() {
        return null;
    }

    @Override
    public void EnviarMail(String destinatario, String asunto, String CuerpoMail) {

    }
}
