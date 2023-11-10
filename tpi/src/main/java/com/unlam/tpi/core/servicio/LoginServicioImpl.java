package com.unlam.tpi.core.servicio;

import com.unlam.tpi.core.interfaces.LoginServicio;
import com.unlam.tpi.core.modelo.UsuarioLogin;
import org.springframework.stereotype.Service;

@Service
public class LoginServicioImpl implements LoginServicio {
    @Override
    public String IniciarSesion(UsuarioLogin usuarioLogin) {
        return null;
    }

    @Override
    public void CerrarSesion(UsuarioLogin usuarioLogin) {

    }
}
