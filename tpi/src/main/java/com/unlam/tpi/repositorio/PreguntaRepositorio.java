package com.unlam.tpi.repositorio;

import java.util.List;

import com.unlam.tpi.model.Pregunta;

public interface PreguntaRepositorio {

    public void guardar(Pregunta pregunta);

    public Pregunta obtener(Long oid);

    public Pregunta modificar(Pregunta pregunta);

    public void borrar(Long id);

	public List<Pregunta> listar();
}
