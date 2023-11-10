package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.UsuarioLogin;

public interface LoginControlador {
    public String IniciarSesion(UsuarioLogin usuarioLogin);
    public void CerrarSesion(UsuarioLogin usuarioLogin);
}
