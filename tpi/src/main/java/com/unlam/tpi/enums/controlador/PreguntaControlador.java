package com.unlam.tpi.enums.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.dto.PreguntaDTO;

public interface PreguntaControlador {
	
	public void guardar(PreguntaDTO pregunta);

	public PreguntaDTO obtener(Long id);

	public void borrar(Long id);

	public ResponseEntity<List<PreguntaDTO>> listar();
}
