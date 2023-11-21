package com.unlam.tpi.delivery.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.core.interfaces.InstrumentoControlador;
import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.modelo.HistoricoInstrumento;

@CrossOrigin
@RestController
@RequestMapping("/instrumento")
public class InstrumentoControladorImpl implements InstrumentoControlador {

	@Autowired
	InstrumentoServicio instrumentoServicio;
	
	@Override
	@GetMapping("/historico/{simbolo}")
	public ResponseEntity<List<HistoricoInstrumento>> getHistoricoDeInstrumento(@PathVariable String simbolo) {
		List<HistoricoInstrumento> historicoInstrumentoRespuesta = instrumentoServicio.getHistoricoInstrumento(simbolo);
		return ResponseEntity.ok(historicoInstrumentoRespuesta);
	}

}
