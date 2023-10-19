package com.unlam.tpi.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.modelo.rest.HistoricoInstrumentoRespuesta;

public interface InstrumentoControlador {

	public ResponseEntity<List<HistoricoInstrumentoRespuesta>> getHistoricoDeInstrumento();

}
