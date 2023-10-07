package com.unlam.tpi.servicio;

import java.util.List;

import javax.transaction.Transactional;

import com.unlam.tpi.dto.PerfilInversorDTO;

public interface PerfilInversorServicio {

	@Transactional
	public PerfilInversorDTO resultadoPerfilSubjetivo(PerfilInversorDTO perfilInversorDTO);

	@Transactional
	public PerfilInversorDTO resultadoNivelConocimiento(PerfilInversorDTO perfilInversorDTO);

	@Transactional
	public PerfilInversorDTO resultadoPerfilInversor(PerfilInversorDTO perfilInversorDTO);
	
	@Transactional
	public void guardar(PerfilInversorDTO perfilInversorDTO);

	@Transactional
	public PerfilInversorDTO obtener(Long id);

	@Transactional
	public void borrar(Long id);

	@Transactional
	public List<PerfilInversorDTO> listar();

}
