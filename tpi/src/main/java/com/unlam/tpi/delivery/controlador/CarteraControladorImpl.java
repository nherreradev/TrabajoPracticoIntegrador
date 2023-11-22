package com.unlam.tpi.delivery.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.interfaces.CarteraControlador;
import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.modelo.RequestCargaDeDinero;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.core.modelo.ValuacionTotalRespuesta;

@CrossOrigin
@RestController
@RequestMapping("/cartera")
public class CarteraControladorImpl implements CarteraControlador {

	@Autowired
	PosicionServicio posicionServicio;

	@Autowired
	AutenticacionService autenticacionServicio;

	@Override
	@GetMapping("/valuacion/total")
	public ResponseEntity<ValuacionTotalRespuesta> getValuacionTotal(
			@RequestHeader("Authorization") String headerAuthorization) throws JsonProcessingException {
		String token = getToken(headerAuthorization);
		Usuario usuario = autenticacionServicio.obtenerDatosUsuarioByToken(token);
		ValuacionTotalRespuesta valuacionTotalRespuesta = posicionServicio.getValuacionTotal(usuario.getOid());
		return ResponseEntity.ok(valuacionTotalRespuesta);
	}

	@Override
	@PostMapping("/acreditar/dinero")
	public ResponseEntity<String> acreditarDinero(@RequestHeader("Authorization") String headerAuthorization, @RequestBody RequestCargaDeDinero request) throws JsonProcessingException {
		String token = getToken(headerAuthorization);
		Usuario usuario = autenticacionServicio.obtenerDatosUsuarioByToken(token);
		request.setUsuarioOid(usuario.getOid());	
		posicionServicio.acreditarDinero(request);
		return ResponseEntity.ok("Dinero acreditado correctamente");
	}
	
	private String getToken(String headerAuthorization) {
		String token = headerAuthorization.replaceAll("Bearer ", "");
		return token;
	}
}
