package com.unlam.tpi.servicio;

import java.util.List;

import com.unlam.tpi.dto.RespuestaDTO;

public interface RespuestaServicio {

    public void guardar(RespuestaDTO respuesta);

    public RespuestaDTO obtener(Long id);

    public void borrar(Long id);

	public List<RespuestaDTO> listar();
}
