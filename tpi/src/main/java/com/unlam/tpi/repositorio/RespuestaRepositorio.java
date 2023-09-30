package com.unlam.tpi.repositorio;

import java.util.List;

import com.unlam.tpi.model.Respuesta;

public interface RespuestaRepositorio {

    public void guardar(Respuesta respuesta);

    public Respuesta obtener(Long oid);

    public Respuesta modificar(Respuesta respuesta);

    public void borrar(Long id);

	public List<Respuesta> listar();
}
