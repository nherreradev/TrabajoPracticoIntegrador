package com.unlam.tpi.servicio;

import java.util.List;

import com.unlam.tpi.model.Pregunta;
import com.unlam.tpi.model.PreguntaDTO;

public interface PreguntaServicio {

    public void guardar(PreguntaDTO pregunta);

    public PreguntaDTO obtener(Long id);

    public PreguntaDTO modificar(PreguntaDTO pregunta);

    public void borrar(Long id);

	public List<PreguntaDTO> listar();
}
