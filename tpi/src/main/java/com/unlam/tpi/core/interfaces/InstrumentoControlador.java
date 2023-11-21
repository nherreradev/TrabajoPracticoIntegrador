package com.unlam.tpi.core.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.core.modelo.HistoricoInstrumento;

public interface InstrumentoControlador {

	public ResponseEntity<List<HistoricoInstrumento>> getHistoricoDeInstrumento(String simbolo);

}
