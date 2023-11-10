package com.unlam.tpi.delivery.controlador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unlam.tpi.core.interfaces.UsuarioControlador;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.Usuario;

@RestController
@RequestMapping("/api")
public class UsuarioControladorImpl implements UsuarioControlador {
	ResponseAPI response = new ResponseAPI();
	@Autowired
	private UsuarioServicio usuarioServicio;

	@Value("${dolar.prediccion.url}")
	private String url_python;
	@Value("${spring.datasource.url}")
	private String url_bd;

	@Override
	@GetMapping("/index")
	public String bienvenido() {
		return "¡Bienvenido al índice de la API! 28/10 14:36 test deploy ....a " + url_python + "\n" + url_bd;
	}

	@Override
	@PostMapping("/guardar-usuario")
	public ResponseEntity<ResponseAPI> RegistrarUsuario(@RequestBody UsuarioRestDTO usuarioRegistro) throws Exception {
		if (this.usuarioServicio.ExisteUsuario(usuarioRegistro.getEmail()))
			return new ResponseEntity<>(response.RecursoYaExistente(), response.RecursoYaExistente().getStatus());
		else
			this.usuarioServicio.GuardarUsuario(usuarioRegistro);
		return new ResponseEntity<>(response.MensajeDeExito(), response.MensajeDeExito().getStatus());
	}

	@Override
	@GetMapping("/obtener-usuario/{email}")
	public ResponseEntity<Usuario> ObtenerDatosUsuarioPorEmail(@PathVariable String email) {
		Usuario usuario = this.usuarioServicio.ObtenerUsuarioPorEmail(email);
		return (usuario == null) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
				: new ResponseEntity<>(usuario, HttpStatus.OK);
	}

	@Override
	@PostMapping("/modificar-usuario")
	public ResponseEntity<ResponseAPI> ModificarUsuario(@RequestBody Usuario usuario) {
		ResponseAPI response = this.usuarioServicio.ModificarUsuario(usuario);
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
		if (!this.usuarioServicio.UsuarioValidado(token)) {
			return new ResponseEntity<>(response.MensajeDeErrorEnRequest(),
					response.MensajeDeErrorEnRequest().getStatus());
		}
		// TODO: VALIDAR SI YA ESTA ACTIVADA LA CUENTA
		return null;
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<String> Login(@RequestBody Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String token = this.usuarioServicio.getTokenLoginUsuario(usuario.getEmail(), usuario.getPass());
        String jsonString = "{\"token\":"+token+"}";

        JsonObject json = new Gson().fromJson(jsonString, JsonObject.class);
		
		return ResponseEntity.ok(json.toString());
	}

}
