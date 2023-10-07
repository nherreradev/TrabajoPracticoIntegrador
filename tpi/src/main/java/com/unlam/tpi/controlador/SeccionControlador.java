package com.unlam.tpi.controlador;

import java.util.List;

import com.unlam.tpi.dto.SeccionDTO;


public interface SeccionControlador {

    public void guardar(SeccionDTO categoria);

    public SeccionDTO obtener(Long id);

    public void borrar(Long id);

	public List<SeccionDTO> listar();
}
