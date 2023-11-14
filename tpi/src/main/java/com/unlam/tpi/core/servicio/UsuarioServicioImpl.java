package com.unlam.tpi.core.servicio;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.interfaces.MailServicio;
import com.unlam.tpi.core.interfaces.UsuarioRepositorio;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.JWTRestDTO;
import com.unlam.tpi.delivery.dto.UsuarioDTO;
import com.unlam.tpi.delivery.dto.UsuarioMapper;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;

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
		String token = this.autenticacionService.GenerarTokenValidacionCuenta(usuarioRestDTO);
		Usuario nuevo = CrearUsuario(usuarioRestDTO, token);
		this.usuarioRepositorio.save(nuevo);
		this.mailServicio.PrepararMailYEnviar(usuarioRestDTO, token);
	}

	// TODO agregar nickname
	private Usuario CrearUsuario(UsuarioRestDTO usuarioRestDTO, String token) {
		Usuario usuario = UsuarioMapper.UsuarioRest2UsuarioModel(usuarioRestDTO);
		usuario.setTokenValidacion(token);
		// usuario.setNombreUsuario(usuario.g);
		usuario.setCuentaConfirmada(Boolean.FALSE);
		usuario.setActivo(Boolean.TRUE);
		usuario.setPremium(Boolean.FALSE);
		return usuario;
	}

	@Override
	public Boolean ExisteUsuario(String email) {
		return this.usuarioRepositorio.existsByEmail(email);
	}

	@Override
	public Usuario ObtenerUsuarioPorEmail(String email) {
		return this.usuarioRepositorio.getUsuarioByEmail(email);
	}

	@Override
	public Usuario ObtenerUsuarioPorNombreUsuario(String nombreUsuario) {
		Usuario usaurio = this.usuarioRepositorio.findByNombreUsuario(nombreUsuario);
		if (usaurio == null) {
			throw new ServiceException("Usuario no encontrado: " + nombreUsuario);
		}
		return usaurio;
	}

	@Override
	public UsuarioDTO ObtenerUsuarioDTOPorNombreUsuario(String nombreUsuario) {
		Usuario usaurio = ObtenerUsuarioPorNombreUsuario(nombreUsuario);
		if (usaurio == null) {
			throw new ServiceException("Error obteniendo el usuario: " + nombreUsuario);
		}
		return UsuarioMapper.entidadADTO(usaurio);
	}

	@Override
	public boolean UsuarioValidado(String token) throws JsonProcessingException {
		JWTRestDTO res = this.autenticacionService.ObtenerClaimsToken(token);
		if (res.getEmailUsuario() != null) {
			Usuario buscado = ObtenerUsuarioPorEmail(res.getEmailUsuario());
			buscado.setCuentaConfirmada(Boolean.TRUE);
			this.usuarioRepositorio.save(buscado);
			return true;
		}
		return false;
	}

	@Override
	public ResponseAPI ModificarUsuario(Usuario usuario) {
		Usuario buscado = this.usuarioRepositorio.getUsuarioByEmail(usuario.getEmail());
		if (buscado != null) {
			buscado.setNombre(usuario.getNombre());
			buscado.setApellido(usuario.getApellido());
			this.usuarioRepositorio.save(buscado);
			return responseAPI.MensajeDeExito();
		}
		return responseAPI.MensajeDeErrorRecursoNoEncontrado();
	}

	@Override
	public ResponseAPI DarDeBajaUsuario(Usuario usuario) {
		if (!ExisteUsuario(usuario.getEmail()))
			return responseAPI.MensajeDeErrorRecursoNoEncontrado();
		Usuario buscado = this.usuarioRepositorio.getUsuarioByEmail(usuario.getEmail());
		if (buscado != null) {
			buscado.setActivo(Boolean.FALSE);
			buscado.setCuentaConfirmada(Boolean.FALSE);
			this.usuarioRepositorio.save(buscado);
			return responseAPI.MensajeDeExito();
		}
		return responseAPI.MensajeDeErrorEnRequest();
	}

	@Override
	public void ConfirmarCuenta(Usuario usuario) {
		usuario.setCuentaConfirmada(Boolean.TRUE);
		this.usuarioRepositorio.save(usuario);
	}

}
