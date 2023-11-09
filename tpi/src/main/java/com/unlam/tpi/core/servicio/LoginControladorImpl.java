package com.unlam.tpi.core.servicio;

import com.unlam.tpi.core.interfaces.LoginControlador;
import com.unlam.tpi.core.interfaces.LoginServicio;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.UsuarioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginControladorImpl implements LoginControlador {
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    LoginServicio loginServicio;


    @Override
    public String IniciarSesion(UsuarioLogin usuarioLogin) {
        return null;
    }

    @Override
    public void CerrarSesion(UsuarioLogin usuarioLogin) {

    }
}
