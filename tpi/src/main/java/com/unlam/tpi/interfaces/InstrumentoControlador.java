package com.unlam.tpi.interfaces;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.modelo.rest.HistoricoInstrumentoRespuesta;

public interface InstrumentoControlador {

	public ResponseEntity<HistoricoInstrumentoRespuesta> getHistoricoDeInstrumento();

}
