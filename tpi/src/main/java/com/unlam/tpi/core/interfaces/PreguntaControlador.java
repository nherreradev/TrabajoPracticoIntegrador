package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.delivery.dto.PreguntaDTO;

public interface PreguntaControlador {
	
	public void guardar(PreguntaDTO pregunta);

	public PreguntaDTO obtener(Long id);

	public void borrar(Long id);

	public ResponseEntity<List<PreguntaDTO>> listar();

	public ResponseEntity<List<PreguntaDTO>> listarPorCategoria(String categoria);

	public void cargaDesdeExcel(MultipartFile excelPregunta) throws IOException;
}
