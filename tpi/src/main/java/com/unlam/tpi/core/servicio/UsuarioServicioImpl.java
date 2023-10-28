package com.unlam.tpi.core.servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.delivery.dto.UsuarioDTO;
import com.unlam.tpi.infraestructura.arquitectura.ServiceException;
import com.unlam.tpi.infraestructura.modelo.ResponseAPI;
import com.unlam.tpi.infraestructura.modelo.Usuario;
import com.unlam.tpi.infraestructura.repositorio.UsuarioRepositorio;

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
	public Usuario ObtenerUsuarioPorNombreUsuario(String nombreUsuario) {
		try {
			Usuario usaurio = this.usuarioRepositorio.findByNombreUsuario(nombreUsuario);
			if (usaurio == null) {
				throw new ServiceException("Usuario no encontrado: " + nombreUsuario);
			}
			return usaurio;
		} catch (Exception e) {
			throw new ServiceException("Usuario no encontrado: " + nombreUsuario);
		}
	}
	
	@Override
	public UsuarioDTO ObtenerUsuarioDTOPorNombreUsuario(String nombreUsuario) {
		try {
			Usuario usaurio = ObtenerUsuarioPorNombreUsuario(nombreUsuario);
			if (usaurio == null) {
				return null;
			} 
			return UsuarioDTO.entidadADTO(usaurio);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error obteniendo el usuario: " + nombreUsuario);
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
