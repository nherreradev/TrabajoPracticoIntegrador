package com.unlam.tpi.servicio;

import java.util.List;

import javax.transaction.Transactional;

import com.unlam.tpi.dto.SeccionDTO;


public interface SeccionServicio {

	@Transactional
	public void guardar(SeccionDTO seccion);

	@Transactional
	public SeccionDTO obtener(Long id);

	@Transactional
	public void borrar(Long id);

	@Transactional
	public List<SeccionDTO> listar();
}
