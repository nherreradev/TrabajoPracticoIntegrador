package com.unlam.tpi.repositorio;

import java.util.List;

import com.unlam.tpi.model.Contenido;

public interface ContenidoRepositorio {
	
    public void guardar(Contenido Contenido);

    public Contenido obtener(Long oid);

    public Contenido modificar(Contenido Contenido);

    public void borrar(Long id);

	public List<Contenido> listar();

}
