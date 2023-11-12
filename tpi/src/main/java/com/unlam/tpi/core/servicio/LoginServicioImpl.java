package com.unlam.tpi.core.servicio;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.interfaces.LoginServicio;
import com.unlam.tpi.core.interfaces.UsuarioRepositorio;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.core.modelo.UsuarioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class LoginServicioImpl implements LoginServicio {
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    AutenticacionService autenticacionService;


    @Override
    public String IniciarSesion(UsuarioLogin usuarioLogin) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return GetTokenLoginUsuario(usuarioLogin);
    }


    private String GetTokenLoginUsuario(UsuarioLogin usuarioLogin) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Usuario buscado = usuarioRepositorio.findByEmailAndPass(usuarioLogin.getMail(), usuarioLogin.getPass());
        try {
            String token = this.autenticacionService.GenerarTokenLoginUsuario(buscado);
            String jsonString = "{\"token\":"+token+"}";
            return new Gson().fromJson(jsonString, JsonObject.class).toString();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
