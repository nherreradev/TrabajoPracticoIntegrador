package com.unlam.tpi.servicio;
import com.unlam.tpi.modelo.persistente.Usuario;
import com.unlam.tpi.modelo.rest.ResponseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.interfaces.UsuarioServicio;
import com.unlam.tpi.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {
	ResponseAPI responseAPI = new ResponseAPI();
	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Override
	public void GuardarUsuario(Usuario usuario) throws Exception {
		if(ExisteUsuario(usuario)) throw new Exception("Usuario ya existente");
		try {
			usuario.setActivo(Boolean.TRUE);
			this.usuarioRepositorio.save(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public Boolean ExisteUsuario(Usuario usuario) { return this.usuarioRepositorio.existsByEmail(usuario.getEmail()); }

	@Override
	public Usuario ObtenerUsuarioPorEmail(String email) {
		try{
			Usuario buscado = this.usuarioRepositorio.getUsuarioByEmail(email);
			if (buscado == null) {
				return null;
			}else
				return buscado;
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ResponseAPI ModificarUsuario(Usuario usuario) {
		try {
			Usuario buscado = this.usuarioRepositorio.getUsuarioByEmail(usuario.getEmail());
			if (buscado != null){
				buscado.setNombre(usuario.getNombre());
				buscado.setApellido(usuario.getApellido());
				this.usuarioRepositorio.save(buscado);
				return responseAPI.MensajeDeExito();
			}else
				return responseAPI.MensajeDeErrorRecursoNoEncontrado();
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("ERROR AL MODIFICAR USUARIO: " + e);
			return responseAPI.MensajeDeErrorRecursoNoEncontrado();
		}
	}

	@Override
	public ResponseAPI DarDeBajaUsuario(Usuario usuario)  {
		if(!ExisteUsuario(usuario)) return responseAPI.MensajeDeErrorRecursoNoEncontrado();
		try{
			Usuario buscado = this.usuarioRepositorio.getUsuarioByEmail(usuario.getEmail());
			if (buscado != null){
				buscado.setActivo(Boolean.FALSE);
				buscado.setCuentaConfirmada(Boolean.FALSE);
				this.usuarioRepositorio.save(buscado);
				return responseAPI.MensajeDeExito();
			}else
				return responseAPI.MensajeDeErrorEnRequest();
		}catch (Exception e ){
			e.printStackTrace();
			System.out.println("ERROR AL DAR DE BAJA USUARIO: " + e);
		}
		return responseAPI.MensajeDeErrorInterno();
	}
	@Override
	public void ConfirmarCuenta(Usuario usuario) {
		usuario.setCuentaConfirmada(Boolean.TRUE);
		this.usuarioRepositorio.save(usuario);
	}

}
