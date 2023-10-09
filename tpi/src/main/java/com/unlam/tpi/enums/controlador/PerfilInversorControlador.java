package com.unlam.tpi.enums.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.dto.PerfilInversorDTO;

public interface PerfilInversorControlador {

	public PerfilInversorDTO resultadoPerfilSubjetivo(PerfilInversorDTO PerfilInversorDTO);

	public PerfilInversorDTO resultadoPerfilInversor(PerfilInversorDTO PerfilInversorDTO);

	public PerfilInversorDTO resultadoNivelConocimiento(PerfilInversorDTO perfilInversorDTO);
	
	public void guardar(PerfilInversorDTO perfilInversorDTO);

	public PerfilInversorDTO obtener(Long id);

	public void borrar(Long id);

	public ResponseEntity<List<PerfilInversorDTO>> listar();

}
