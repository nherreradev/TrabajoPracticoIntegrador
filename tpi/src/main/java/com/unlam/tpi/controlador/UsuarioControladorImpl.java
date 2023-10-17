package com.unlam.tpi.controlador;

import com.unlam.tpi.modelo.persistente.Usuario;
import com.unlam.tpi.modelo.rest.ResponseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unlam.tpi.interfaces.UsuarioServicio;

@RestController
@RequestMapping("/api")
public class UsuarioControladorImpl implements UsuarioControlador {
	ResponseAPI response = new ResponseAPI();
	@Autowired
	private UsuarioServicio usuarioServicio;

	@Override
	@GetMapping("/index")
	public String bienvenido() {
		return "¡Bienvenido al índice de la API!";
	}

	@Override
	@PostMapping("/guardar-usuario")
	public ResponseEntity<ResponseAPI> RegistrarUsuario(@RequestBody Usuario usuario) throws Exception {
		if(this.usuarioServicio.ExisteUsuario(usuario))
			return new ResponseEntity<>(response.RecursoYaExistente(), response.RecursoYaExistente().getStatus());
			else
				this.usuarioServicio.GuardarUsuario(usuario);
			return new ResponseEntity<>(response.MensajeDeExito(), response.MensajeDeExito().getStatus());
	}

	@Override
	@GetMapping("/obtener-usuario/{email}")
	public ResponseEntity<Usuario> ObtenerDatosUsuarioPorEmail(@PathVariable String email) {
		Usuario usuario = this.usuarioServicio.ObtenerUsuarioPorEmail(email);
		return (usuario == null) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(usuario, HttpStatus.OK);
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

}
