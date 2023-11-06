package com.unlam.tpi.core.interfaces;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.unlam.tpi.core.modelo.RequestPorcentaje;
import com.unlam.tpi.core.modelo.ResponsePorcentaje;

public interface RendimientoControlador {

	public ResponseEntity<Map<String, ResponsePorcentaje>> calcularPorcentajeGananciaPerdidaDeTodosLosInstrumentosEnCartera(
			@RequestBody RequestPorcentaje request);

}
