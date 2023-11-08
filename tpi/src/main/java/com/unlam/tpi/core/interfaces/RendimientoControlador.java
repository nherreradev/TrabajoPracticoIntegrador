package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.unlam.tpi.core.modelo.RequestPorcentaje;
import com.unlam.tpi.core.modelo.RendimientoActualResponse;

public interface RendimientoControlador {

	public ResponseEntity<RendimientoActualResponse> calcularRendimientoInstrumentosEnCarteraDiaDeHoy(
			@RequestBody RequestPorcentaje request);

}
