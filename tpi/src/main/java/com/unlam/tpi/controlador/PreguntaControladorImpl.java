package com.unlam.tpi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.dto.PreguntaDTO;
import com.unlam.tpi.interfaces.PreguntaServicio;

@RestController
@RequestMapping("/api/pregunta")
public class PreguntaControladorImpl implements PreguntaControlador {

	@Autowired
	private PreguntaServicio preguntaServicio;

	@Override
	@PostMapping(value = "/guardar", consumes = "application/json", produces = "application/json")
	public void guardar(@RequestBody PreguntaDTO pregunta) {
		getPreguntaServicio().guardar(pregunta);
	}

	@Override
	@GetMapping("/obtener")
	public PreguntaDTO obtener(Long id) {
		return getPreguntaServicio().obtener(id);
	}

	@Override
	@GetMapping("/borrar")
	public void borrar(Long id) {
		getPreguntaServicio().borrar(id);
	}

	@Override
	@GetMapping("/listar")
	public ResponseEntity<List<PreguntaDTO>> listar() {
		return ResponseEntity.ok(getPreguntaServicio().listar());
	}

	@Override
	@GetMapping("/listar-por-categoria")
	public ResponseEntity<List<PreguntaDTO>> listarPorCategoria(String categoria) {
		return ResponseEntity.ok(getPreguntaServicio().listarPorCategoria(categoria));
	}

	public PreguntaServicio getPreguntaServicio() {
		return preguntaServicio;
	}

	public void setPreguntaServicio(PreguntaServicio preguntaServicio) {
		this.preguntaServicio = preguntaServicio;
	}

}
