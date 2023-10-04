package com.unlam.tpi.servicio;

import java.util.List;

import com.unlam.tpi.dto.PreguntaDTO;

public interface PreguntaServicio {

    public void guardar(PreguntaDTO pregunta);

    public PreguntaDTO obtener(Long id);

    public void borrar(Long id);

	public List<PreguntaDTO> listar();
}
