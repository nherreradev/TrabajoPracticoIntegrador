package com.unlam.tpi.repositorio;

import java.util.List;

import com.unlam.tpi.model.Nivel;

public interface NivelRepositorio {
	
    public void guardar(Nivel nivel);

    public Nivel obtener(Long oid);

    public Nivel modificar(Nivel nivel);

    public void borrar(Long id);

	public List<Nivel> listar();

}
