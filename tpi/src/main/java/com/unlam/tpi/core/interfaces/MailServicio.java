package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.Usuario;

public interface MailServicio {
    void prepararMailYEnviar(Usuario usuario, String token);
    void enviarMail(String destinatario, String asunto, String CuerpoMail);
	void envioMailRecuperacionCuenta(String nombre, String email, String token);
}
