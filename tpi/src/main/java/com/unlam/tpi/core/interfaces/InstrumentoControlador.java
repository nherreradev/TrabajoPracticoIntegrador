package com.unlam.tpi.core.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.infraestructura.modelo.HistoricoInstrumentoRespuesta;

public interface InstrumentoControlador {

	public ResponseEntity<List<HistoricoInstrumentoRespuesta>> getHistoricoDeInstrumento(String simbolo);

}
