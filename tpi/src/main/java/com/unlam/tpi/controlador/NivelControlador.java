package com.unlam.tpi.controlador;

import java.util.List;

import com.unlam.tpi.model.Nivel;

public interface NivelControlador {

    public void guardar(Nivel nivel);

    public Nivel obtener(Long id);

    public Nivel modificar(Nivel nivel);

    public void borrar(Long id);

	public List<Nivel> listar();
}
