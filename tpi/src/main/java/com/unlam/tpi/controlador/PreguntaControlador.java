package com.unlam.tpi.controlador;

import java.util.List;

import com.unlam.tpi.dto.PreguntaDTO;

public interface PreguntaControlador {
	public void guardar(PreguntaDTO pregunta);

	public PreguntaDTO obtener(Long id);

	public void borrar(Long id);

	public List<PreguntaDTO> listar();
}
