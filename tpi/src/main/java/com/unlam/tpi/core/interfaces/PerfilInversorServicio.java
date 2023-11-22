package com.unlam.tpi.core.interfaces;

import java.util.List;

import javax.transaction.Transactional;

import com.unlam.tpi.core.modelo.PerfilInversor;

public interface PerfilInversorServicio {

	@Transactional
	public PerfilInversor resultadoPerfilSubjetivo(PerfilInversor perfilInversorDTO);

	@Transactional
	public PerfilInversor resultadoNivelConocimiento(PerfilInversor perfilInversorDTO);

	@Transactional
	public PerfilInversor resultadoPerfilInversor(PerfilInversor perfilInversorDTO);
	
	@Transactional
	public PerfilInversor obtener(Long id);

	@Transactional
	public void borrar(Long id);

	@Transactional
	public List<PerfilInversor> listar();

	@Transactional
	public byte[] obtenerCertificado(String nombreUsuario);

	@Transactional
	public PerfilInversor guardar(PerfilInversor perfilInversor);

	public PerfilInversor listarporUsuario(Long id);

}
