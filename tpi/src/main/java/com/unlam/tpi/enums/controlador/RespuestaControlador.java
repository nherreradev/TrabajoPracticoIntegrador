package com.unlam.tpi.enums.controlador;

import java.util.List;

import com.unlam.tpi.dto.RespuestaDTO;

public interface RespuestaControlador {
	
    public void guardar(RespuestaDTO respuesta);

    public RespuestaDTO obtener(Long id);

    public void borrar(Long id);

	public List<RespuestaDTO> listar();
}
