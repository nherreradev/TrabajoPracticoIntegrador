package com.unlam.tpi.controlador;

import java.util.List;

import com.unlam.tpi.model.Contenido;
import com.unlam.tpi.model.ContenidoDTO;

public interface ContenidoControlador {

    public void guardar(ContenidoDTO Contenido);

    public ContenidoDTO obtener(Long id);

    public ContenidoDTO modificar(ContenidoDTO Contenido);

    public void borrar(Long id);

	public List<ContenidoDTO> listar();
	
}
