package com.unlam.tpi.servicio;

import java.util.List;

import com.unlam.tpi.model.Contenido;
import com.unlam.tpi.model.ContenidoDTO;

public interface ContenidoServicio {

    public void guardar(ContenidoDTO Contenido);

    public ContenidoDTO obtener(Long id);

    public ContenidoDTO modificar(ContenidoDTO Contenido);

    public void borrar(Long id);

	public List<ContenidoDTO> listar();
	
}
