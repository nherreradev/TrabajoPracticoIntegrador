package com.unlam.tpi.interfaces;

import java.util.List;

import javax.transaction.Transactional;

import com.unlam.tpi.dto.RespuestaDTO;

public interface RespuestaServicio {

	@Transactional
    public void guardar(RespuestaDTO respuesta);

    @Transactional
    public RespuestaDTO obtener(Long id);

    @Transactional
    public void borrar(Long id);

    @Transactional
	public List<RespuestaDTO> listar();
}
