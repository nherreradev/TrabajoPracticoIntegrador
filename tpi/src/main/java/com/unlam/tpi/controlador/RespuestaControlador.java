package com.unlam.tpi.controlador;

import java.util.List;

import com.unlam.tpi.model.Respuesta;

public interface RespuestaControlador {
	
    public void guardar(Respuesta respuesta);

    public Respuesta obtener(Long id);

    public Respuesta modificar(Respuesta respuesta);

    public void borrar(Long id);

	public List<Respuesta> listar();
}
