package com.unlam.tpi.core.servicio;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.interfaces.MailServicio;
import com.unlam.tpi.core.interfaces.UsuarioRepositorio;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.JWTRestDTO;
import com.unlam.tpi.delivery.dto.PasswordDto;
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
	public void guardarUsuario(UsuarioRestDTO usuarioRestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String token = this.autenticacionService.GenerarTokenValidacionCuenta(usuarioRestDTO.getEmail());
		Usuario nuevo = crearUsuario(usuarioRestDTO, token);
		this.usuarioRepositorio.save(nuevo);
		this.mailServicio.prepararMailYEnviar(usuarioRestDTO, token);
	}

	private Usuario crearUsuario(UsuarioRestDTO usuarioRestDTO, String token) {
		Usuario usuario = UsuarioMapper.UsuarioRest2UsuarioModel(usuarioRestDTO);
		usuario.setTokenValidacion(token);
		usuario.setCuentaConfirmada(Boolean.FALSE);
		usuario.setActivo(Boolean.TRUE);
		usuario.setPremium(Boolean.FALSE);
		usuario.setEsAdministrador(Boolean.FALSE);
		return usuario;
	}

	@Override
	public Boolean existeEmail(String email) {
		return this.usuarioRepositorio.existsByEmail(email);
	}
	@Override
	public Boolean existeNombreUsuario(String nombreUsuario) {
		return this.usuarioRepositorio.existsByNombreUsuario(nombreUsuario);
	}

	@Override
	public Usuario obtenerUsuarioPorEmail(String email) {
		return this.usuarioRepositorio.getUsuarioByEmail(email);
	}
	
	@Override
	public Usuario obtenerUsuarioPorToken(String token) {
		return this.usuarioRepositorio.getUsuarioByTokenValidacion(token);
	}
	

	@Override
	public Usuario obtenerUsuarioPorNombreUsuario(String nombreUsuario) {
		Usuario usaurio = this.usuarioRepositorio.findByNombreUsuario(nombreUsuario);
		if (usaurio == null) {
			throw new ServiceException("Usuario no encontrado: " + nombreUsuario);
		}
		return usaurio;
	}

	@Override
	public UsuarioDTO obtenerUsuarioDTOPorNombreUsuario(String nombreUsuario) {
		Usuario usaurio = obtenerUsuarioPorNombreUsuario(nombreUsuario);
		if (usaurio == null) {
			throw new ServiceException("Error obteniendo el usuario: " + nombreUsuario);
		}
		return UsuarioMapper.entidadADTO(usaurio);
	}

	@Override
	public boolean usuarioValidadoPorPrimeraVez(String token) {
		JWTRestDTO res = this.autenticacionService.obtenerClaimsToken(token);
		if (res.getEmailUsuario() != null) {
			Usuario buscado = obtenerUsuarioPorEmail(res.getEmailUsuario());
			buscado.setCuentaConfirmada(Boolean.TRUE);
			this.usuarioRepositorio.save(buscado);
			return true;
		}
		return false;
	}

	@Override
	public Boolean elUsuarioFueYaEstaValidado(String token) {
		JWTRestDTO UsuarioToken = autenticacionService.obtenerClaimsToken(token);
		Usuario usuario = usuarioRepositorio.getUsuarioByEmail(UsuarioToken.getEmailUsuario());
		return usuario.getCuentaConfirmada();
	}

	@Override
	public ResponseAPI modificarUsuario(Usuario usuario) {
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
		if (!existeEmail(usuario.getEmail()))
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
	public void recuperarCuenta(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException {
		if (!existeEmail(usuario.getEmail()))
			return;
		Usuario buscado = this.usuarioRepositorio.getUsuarioByEmail(usuario.getEmail());
		if (buscado != null) {
			String token = this.autenticacionService.GenerarSecretJWT();
			buscado.setActivo(Boolean.FALSE);
			buscado.setCuentaConfirmada(Boolean.FALSE);
			buscado.setTokenValidacion(token);
			this.usuarioRepositorio.save(buscado);
			this.mailServicio.envioMailRecuperacionCuenta(buscado.getNombre(),buscado.getEmail(), token);
		}
	}

	@Override
	public Boolean cambioPassword(PasswordDto passwordDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Usuario usuario = obtenerUsuarioPorToken(passwordDto.getToken());
		if (usuario == null) {
			return Boolean.FALSE;
		}
		String token = this.autenticacionService.GenerarSecretJWT();
		usuario.setCuentaConfirmada(Boolean.TRUE);
		usuario.setActivo(Boolean.TRUE);
		usuario.setPass(passwordDto.getNewPassword());
		usuario.setTokenValidacion(token);
		this.usuarioRepositorio.save(usuario);
		return Boolean.TRUE;
	}
	
	
	@Override
	public void confirmarCuenta(Usuario usuario) {
		usuario.setCuentaConfirmada(Boolean.TRUE);
		this.usuarioRepositorio.save(usuario);
	}

	@Override
	public List<Usuario> obtenerTodosLosUsuarios() {
		return usuarioRepositorio.findAll();
	}

}