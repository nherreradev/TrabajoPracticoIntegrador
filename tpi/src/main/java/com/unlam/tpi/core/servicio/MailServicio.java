package com.unlam.tpi.core.servicio;

import com.unlam.tpi.infraestructura.modelo.Usuario;

public interface MailServicio {
    void PrepararMail(Usuario usuario);
    void EnviarMail(String destinatario, String asunto, String CuerpoMail);
}
