package com.unlam.tpi.core.servicio;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.interfaces.MailServicio;
import com.unlam.tpi.delivery.dto.JWTRestDTO;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.UsuarioRepositorio;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.UsuarioDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {
	ResponseAPI responseAPI = new ResponseAPI();
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	@Autowired
	AutenticacionService autenticacionService;
	@Autowired
	MailServicio mailServicio;
	@Override
	public void GuardarUsuario(UsuarioRestDTO usuarioRestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Usuario nuevo;
		try {
			String token = this.autenticacionService.GenerarTokenValidacionCuenta(usuarioRestDTO);
			nuevo = CrearUsuario(usuarioRestDTO, token);
			this.usuarioRepositorio.save(nuevo);
			this.mailServicio.PrepararMailYEnviar(usuarioRestDTO, token);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	// TODO agregar nickname
	private Usuario CrearUsuario(UsuarioRestDTO usuarioRestDTO, String token) {
		Usuario usuario = usuarioRestDTO.UsuarioRest2UsuarioModel(usuarioRestDTO);
		usuario.setTokenValidacion(token);
		//usuario.setNombreUsuario(usuario.g);
		usuario.setCuentaConfirmada(Boolean.FALSE);
		usuario.setActivo(Boolean.TRUE);
		usuario.setPremium(Boolean.FALSE);
		return usuario;
	}

	@Override
	public Boolean ExisteUsuario(String email) { return this.usuarioRepositorio.existsByEmail(email); }

	@Override
	public Usuario ObtenerUsuarioPorEmail(String email) {
		UsuarioDTO usuarioDto = null;
		try{
			Usuario buscado = this.usuarioRepositorio.getUsuarioByEmail(email);
			if (buscado == null) {
				return null;
			}else
				usuarioDto = usuarioDto.entidadADTO(buscado);
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
	public boolean UsuarioValidado(String token) throws JsonProcessingException {
		JWTRestDTO res = this.autenticacionService.ObtenerClaimsToken(token);
		try{
			if (res != null) {
				Usuario buscado = ObtenerUsuarioPorEmail(res.getEmailUsuario());
				buscado.setCuentaConfirmada(Boolean.TRUE);
				this.usuarioRepositorio.save(buscado);
				return true;
			}
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("ERROR AL ENCONTRAR USUARIO: " + e);
			return false;
		}
		return false;
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
		if(!ExisteUsuario(usuario.getEmail())) return responseAPI.MensajeDeErrorRecursoNoEncontrado();
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
