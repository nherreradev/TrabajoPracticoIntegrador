package com.unlam.tpi.delivery.controlador;

import com.unlam.tpi.core.interfaces.LoginControlador;
import com.unlam.tpi.core.interfaces.LoginServicio;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.UsuarioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/login")
public class LoginControladorImpl implements LoginControlador {
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    LoginServicio loginServicio;


    @Override
    @PostMapping("/iniciar-sesion")
    public ResponseEntity<String> IniciarSesion(@RequestBody UsuarioLogin usuarioLogin) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if(usuarioLogin == null){
            return ResponseEntity.badRequest().body("Error login.");
        }
        String Token = this.loginServicio.IniciarSesion(usuarioLogin);
        return ResponseEntity.ok(Token);
    }

}
