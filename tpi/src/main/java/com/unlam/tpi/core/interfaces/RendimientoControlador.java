package com.unlam.tpi.core.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.unlam.tpi.core.modelo.RendimientoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.modelo.HistoricoRendimientosResponse;
import com.unlam.tpi.core.modelo.RendimientoActualResponse;

public interface RendimientoControlador {

	public ResponseEntity<RendimientoActualResponse> calcularRendimientoInstrumentosEnCarteraDiaDeHoy(
			@RequestHeader("Authorization") String headerAuthorization) throws JsonProcessingException;

	public ResponseEntity<List<HistoricoRendimientosResponse>> calcularRendimientoInstrumentosHistorico(
			@RequestBody RendimientoRequest request, @RequestHeader("Authorization") String headerAuthorization) throws JsonProcessingException;

	public ResponseEntity<String> guardarRendimientoDiario(@RequestBody RendimientoRequest request, @RequestHeader("Authorization") String headerAuthorization) throws JsonProcessingException;

}
