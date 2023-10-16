package com.unlam.tpi.interfaces;

import java.util.List;

import javax.transaction.Transactional;

import com.unlam.tpi.dto.PreguntaDTO;

public interface PreguntaServicio {

	@Transactional
    public void guardar(PreguntaDTO pregunta);

	@Transactional
    public PreguntaDTO obtener(Long id);

	@Transactional
    public void borrar(Long id);

	@Transactional
	public List<PreguntaDTO> listar();

	@Transactional
	public List<PreguntaDTO> listarPorCategoria(String categoria);
}
