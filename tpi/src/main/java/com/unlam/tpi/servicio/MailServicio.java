package com.unlam.tpi.servicio;

import com.unlam.tpi.modelo.persistente.Usuario;

public interface MailServicio {
    void PrepararMail(Usuario usuario);
    void EnviarMail(String destinatario, String asunto, String CuerpoMail);
}
