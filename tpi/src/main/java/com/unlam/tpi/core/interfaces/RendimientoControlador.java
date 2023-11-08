package com.unlam.tpi.core.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.unlam.tpi.core.modelo.RendimientoRequest;
import com.unlam.tpi.core.modelo.HistoricoRendimientosResponse;
import com.unlam.tpi.core.modelo.RendimientoActualResponse;

public interface RendimientoControlador {

	public ResponseEntity<RendimientoActualResponse> calcularRendimientoInstrumentosEnCarteraDiaDeHoy(
			@RequestBody RendimientoRequest request);
	
	public ResponseEntity<List<HistoricoRendimientosResponse>> calcularRendimientoInstrumentosHistorico(
			@RequestBody RendimientoRequest request);

}
