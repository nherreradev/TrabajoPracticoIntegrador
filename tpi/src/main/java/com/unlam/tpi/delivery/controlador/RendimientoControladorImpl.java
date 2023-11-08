package com.unlam.tpi.delivery.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.interfaces.RendimientoControlador;
import com.unlam.tpi.core.modelo.RequestPorcentaje;
import com.unlam.tpi.core.modelo.RendimientoActualResponse;

@CrossOrigin
@RestController
@RequestMapping("/rendimiento")
public class RendimientoControladorImpl implements RendimientoControlador {

	@Autowired
	PosicionServicio posicionServicio;

	@Override
	@PostMapping("/instrumentos/actual")
	public ResponseEntity<RendimientoActualResponse> calcularRendimientoInstrumentosEnCarteraDiaDeHoy(
			@RequestBody RequestPorcentaje request) {
		RendimientoActualResponse rendimientoActualResponse = posicionServicio
				.calcularRendimientoActual(request.getToken());
		return ResponseEntity.ok(rendimientoActualResponse);
	}
	
	

}
