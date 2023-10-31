package com.unlam.tpi.delivery.controlador;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.unlam.tpi.core.interfaces.IAControlador;
import com.unlam.tpi.core.interfaces.IAServicio;
import com.unlam.tpi.core.modelo.Instrumento;

@CrossOrigin
@RestController
@RequestMapping("/IA")
public class IAControladorImpl implements IAControlador {

	@Autowired
	IAServicio iAServicio;

	@Override
	@GetMapping("/txt")
	public ResponseEntity<String> generarArchivoTXT(String tipoPerfil) {
		iAServicio.generarTXT(tipoPerfil);
		return ResponseEntity.ok("OK");
	}

	@Override
	@GetMapping("/portafolio/sugerido")
	public ResponseEntity<String> obtenerPortafolioSugerido(String tipoPerfil, String url) {
		List<Instrumento> instrumentosRecomendados = iAServicio.obtenerPortafolioSugerido(tipoPerfil, url);
		String json = new Gson().toJson(instrumentosRecomendados);
		return ResponseEntity.ok(json);
	}
	
	@Override
	@GetMapping("/portafolio/sugeridoFake")
	public ResponseEntity<String> obtenerPortafolioSugeridoFake(String tipoPerfil) {
		List<Instrumento> instrumentosRecomendados = iAServicio.obtenerPortafolioSugeridoFake(tipoPerfil);
		String json = new Gson().toJson(instrumentosRecomendados);
		return ResponseEntity.ok(json);
	}

}
