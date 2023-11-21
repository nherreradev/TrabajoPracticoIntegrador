package com.unlam.tpi.delivery.controlador;

import java.util.List;

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
import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.interfaces.RendimientoControlador;
import com.unlam.tpi.core.modelo.HistoricoRendimientosResponse;
import com.unlam.tpi.core.modelo.RendimientoActualResponse;
import com.unlam.tpi.core.modelo.RendimientoRequest;
import com.unlam.tpi.delivery.dto.UsuarioDTO;

@CrossOrigin
@RestController
@RequestMapping("/rendimiento")
public class RendimientoControladorImpl implements RendimientoControlador {

	@Autowired
	PosicionServicio posicionServicio;
	
	@Autowired
	AutenticacionService autenticacionServicio;

	@Override
	@GetMapping("/instrumentos/actual")
	public ResponseEntity<RendimientoActualResponse> calcularRendimientoInstrumentosEnCarteraDiaDeHoy( 
			@RequestHeader("Authorization") String headerAuthorization) throws JsonProcessingException {
		String token = headerAuthorization.replaceAll("Bearer ", "");
		UsuarioDTO usuario = autenticacionServicio.obtenerDatosUsuarioByToken(token);
		RendimientoActualResponse rendimientoActualResponse = posicionServicio
				.calcularRendimientoActual(usuario.getOid());
		return ResponseEntity.ok(rendimientoActualResponse);
	}
	
	@Override
	@PostMapping("/instrumentos/historico")
	public ResponseEntity<List<HistoricoRendimientosResponse>> calcularRendimientoInstrumentosHistorico(
			@RequestBody RendimientoRequest request, @RequestHeader("Authorization") String headerAuthorization) throws JsonProcessingException {
		String token = headerAuthorization.replaceAll("Bearer ", "");
		UsuarioDTO usuario = autenticacionServicio.obtenerDatosUsuarioByToken(token);
		List<HistoricoRendimientosResponse> listaDeRendimientosHistoricos = posicionServicio.obtenerRendimientosHistoricosPorSimbolo(request.getSimboloInstrumento(), usuario.getOid());
		return ResponseEntity.ok(listaDeRendimientosHistoricos);
	}
	
	@Override
	@PostMapping("/dia/guardar")
	public ResponseEntity<String> guardarRendimientoDiario(
			@RequestBody RendimientoRequest request, @RequestHeader("Authorization") String headerAuthorization) throws JsonProcessingException {
		String token = headerAuthorization.replaceAll("Bearer ", "");
		UsuarioDTO usuario = autenticacionServicio.obtenerDatosUsuarioByToken(token);
		RendimientoActualResponse rendimientoActualResponse = posicionServicio.calcularRendimientoActual(usuario.getOid());
		posicionServicio.guardarCierresDiarios(rendimientoActualResponse.getRendimientosActuales(), usuario.getOid());;
		return ResponseEntity.ok("Rendimiento guardado correctamente");
	}
	
	

}
