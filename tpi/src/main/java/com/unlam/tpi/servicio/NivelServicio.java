package com.unlam.tpi.servicio;

import java.util.List;

import com.unlam.tpi.model.Nivel;

public interface NivelServicio {

    public void guardar(Nivel nivel);

    public Nivel obtener(Long id);

    public Nivel modificar(Nivel nivel);

    public void borrar(Long id);

	public List<Nivel> listar();
}
