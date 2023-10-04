package com.unlam.tpi.servicio;

import java.util.List;

import com.unlam.tpi.dto.SeccionDTO;


public interface SeccionServicio {

	public void guardar(SeccionDTO seccion);

	public SeccionDTO obtener(Long id);

	public void borrar(Long id);

	public List<SeccionDTO> listar();
}
