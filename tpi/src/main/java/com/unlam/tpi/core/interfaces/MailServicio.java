package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.delivery.dto.UsuarioRestDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface MailServicio {
    void PrepararMailYEnviar(UsuarioRestDTO usuarioRestDTO, String token) throws NoSuchAlgorithmException, InvalidKeySpecException;
    void EnviarMail(String destinatario, String asunto, String CuerpoMail);
	void envioMailRecuperacionCuenta(String nombre, String email, String token);
}
