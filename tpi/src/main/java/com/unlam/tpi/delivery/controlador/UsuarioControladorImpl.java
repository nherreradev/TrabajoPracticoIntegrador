package com.unlam.tpi.delivery.controlador;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.interfaces.UsuarioControlador;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.PasswordDto;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;

@RestController
@RequestMapping("/api")
public class UsuarioControladorImpl implements UsuarioControlador {
	ResponseAPI response = new ResponseAPI();
	@Autowired
	private UsuarioServicio usuarioServicio;

	@Override
	@PostMapping("/guardar-usuario")
	public ResponseEntity<ResponseAPI> RegistrarUsuario(@RequestBody UsuarioRestDTO usuarioRegistro) throws Exception {
		if (this.usuarioServicio.existeEmail(usuarioRegistro.getEmail())
				|| this.usuarioServicio.existeNombreUsuario(usuarioRegistro.getNombreUsuario()))
			return new ResponseEntity<>(response.RecursoYaExistente(), response.RecursoYaExistente().getStatus());
		else
			this.usuarioServicio.guardarUsuario(usuarioRegistro);
		return new ResponseEntity<>(response.MensajeDeExito(), response.MensajeDeExito().getStatus());
	}

	@Override
	@GetMapping("/obtener-usuario/{email}")
	public ResponseEntity<Usuario> ObtenerDatosUsuarioPorEmail(@PathVariable String email) {
		Usuario usuario = this.usuarioServicio.obtenerUsuarioPorEmail(email);
		return (usuario == null) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
				: new ResponseEntity<>(usuario, HttpStatus.OK);
	}

	@Override
	@PostMapping("/modificar-usuario")
	public ResponseEntity<ResponseAPI> ModificarUsuario(@RequestBody Usuario usuario) {
		ResponseAPI response = this.usuarioServicio.modificarUsuario(usuario);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@Override
	@PostMapping("/eliminar-usuario")
	public ResponseEntity<ResponseAPI> DarUsuarioDeBaja(@RequestBody Usuario usuario) {
		ResponseAPI response = this.usuarioServicio.DarDeBajaUsuario(usuario);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@Override
	@PostMapping("/activar-cuenta")
	public ResponseEntity<ResponseAPI> ActivarCuenta(@RequestBody String token) throws JsonProcessingException {
		// TODO: terminar token, buscar por mail y verificar si ya fue activada la
		// cuenta
		if (usuarioServicio.elUsuarioFueYaEstaValidado(token)) {
			return new ResponseEntity<>(response.RecursoYaExistente(), response.RecursoYaExistente().getStatus());
		}
		if (!this.usuarioServicio.usuarioValidadoPorPrimeraVez(token)) {
			return new ResponseEntity<>(response.MensajeDeErrorEnRequest(),
					response.MensajeDeErrorEnRequest().getStatus());
		}
		return new ResponseEntity<>(response.MensajeDeExito(), response.MensajeDeExito().getStatus());
	}

	@Override
	@PostMapping("/cambiar-password")
	public ResponseEntity<ResponseAPI> cambiarPassword(@RequestBody PasswordDto passwordDto) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {
		if (!this.usuarioServicio.cambioPassword(passwordDto)) {
			return new ResponseEntity<>(response.MensajeDeErrorEnRequest(),
					response.MensajeDeErrorEnRequest().getStatus());
		}
		return new ResponseEntity<>(response.MensajeDeExito(), response.MensajeDeExito().getStatus());
	}

	@Override
	@PostMapping("/recuperar-cuenta")
	public ResponseEntity<ResponseAPI> RecuperarCuenta(@RequestBody Usuario usuario)
			throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {
		this.usuarioServicio.recuperarCuenta(usuario);
		return new ResponseEntity<>(response.MensajeDeExito(), response.MensajeDeExito().getStatus());
	}
}