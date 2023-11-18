package com.unlam.tpi.core.servicio;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.interfaces.LoginServicio;
import com.unlam.tpi.core.interfaces.UsuarioRepositorio;
import com.unlam.tpi.core.modelo.ServiceException;
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
        return getTokenLoginUsuario(usuarioLogin);
    }


	private String getTokenLoginUsuario(UsuarioLogin usuarioLogin)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		Usuario usaurio = usuarioRepositorio.findByEmailAndPass(usuarioLogin.getMail(), usuarioLogin.getPass());
		if (usaurio == null) {
			throw new ServiceException("Usuario/Password invalidos ");
		}
		String token = this.autenticacionService.generarTokenLoginUsuario(usaurio);
		String jsonString = "{\"token\":" + token + "}";
		return new Gson().fromJson(jsonString, JsonObject.class).toString();
	}

	@Override
	public String IniciarSesionUsuario(UsuarioLogin usuarioLogin) throws NoSuchAlgorithmException, InvalidKeySpecException{
	     String jsonString="";
		 Usuario buscado = this.usuarioRepositorio.findByEmailAndPass(usuarioLogin.getMail(), usuarioLogin.getPass());
		
		 if(buscado==null) {
		 return jsonString;
		 }
		
		 String token = this.autenticacionService.generarTokenLoginUsuario(buscado);
		 if(buscado.getEsAdministrador() != null && buscado.getEsAdministrador()) {
			 	return jsonString = "{\"token\":\"" + token + "\",\"esAdministrador\":true}";
	            //return new Gson().fromJson(jsonString, JsonObject.class).toString();
		 }
		 
		 		return jsonString = "{\"token\":\"" + token + "\",\"esAdministrador\":false}";
            
		 		//return new Gson().fromJson(jsonString, JsonObject.class).toString();
	
	}
}
