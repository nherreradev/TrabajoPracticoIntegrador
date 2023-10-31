package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.Usuario;

public interface MailServicio {
    void PrepararMail(Usuario usuario);
    void EnviarMail(String destinatario, String asunto, String CuerpoMail);
}
