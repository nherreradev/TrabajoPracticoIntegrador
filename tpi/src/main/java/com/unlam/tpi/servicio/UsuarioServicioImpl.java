package com.unlam.tpi.servicio;
import com.unlam.tpi.modelo.persistente.Usuario;
import com.unlam.tpi.modelo.rest.ResponseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
			return this.usuarioRepositorio.getUsuarioByEmail(email);
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
				return responseAPI.MensajeDeExito();
			}else
				return responseAPI.MensajeDeErrorRecursoNoEncontrado();
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("ERROR AL MODIFICAR USUARIO: " + e);
			return responseAPI.MensajeDeErrorRecursoNoEncontrado();
		}
	}

}
