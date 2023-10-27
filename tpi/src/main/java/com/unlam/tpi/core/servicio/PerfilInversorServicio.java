package com.unlam.tpi.core.servicio;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import com.unlam.tpi.delivery.dto.PerfilInversorDTO;
import com.unlam.tpi.infraestructura.modelo.PerfilInversor;

import net.sf.jasperreports.engine.JRException;

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

	@Transactional
	public byte[] obtenerCertificado(String nombreUsuario) throws JRException, SQLException;

	@Transactional
	public void guardar(PerfilInversor perfilInversor);

}
