package com.unlam.tpi.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.interfaces.InstrumentoControlador;
import com.unlam.tpi.interfaces.InstrumentoServicio;
import com.unlam.tpi.modelo.rest.HistoricoInstrumentoRespuesta;

@CrossOrigin
@RestController
@RequestMapping("/instrumento")
public class InstrumentoControladorImpl implements InstrumentoControlador {

	@Autowired
	InstrumentoServicio instrumentoServicio;
	
	@Override
	@GetMapping("/historico")
	public ResponseEntity<HistoricoInstrumentoRespuesta> getHistoricoDeInstrumento() {

		HistoricoInstrumentoRespuesta historicoInstrumentoRespuesta = instrumentoServicio.getHistoricoInstrumento();
		// posicionServicio.getValuacionTotal();
		return ResponseEntity.ok(historicoInstrumentoRespuesta);
	}

}
