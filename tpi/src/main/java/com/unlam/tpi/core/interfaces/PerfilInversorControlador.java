package com.unlam.tpi.core.interfaces;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.delivery.dto.PerfilInversorDTO;

public interface PerfilInversorControlador {

	public PerfilInversorDTO resultadoPerfilSubjetivo(PerfilInversorDTO PerfilInversorDTO);

	public PerfilInversorDTO resultadoPerfilInversor(PerfilInversorDTO PerfilInversorDTO);

	public PerfilInversorDTO resultadoNivelConocimiento(PerfilInversorDTO perfilInversorDTO);
	
	public void guardar(PerfilInversorDTO perfilInversorDTO);

	public PerfilInversorDTO obtener(Long id);

	public void borrar(Long id);

	public ResponseEntity<List<PerfilInversorDTO>> listar();

	public void obtenerCertificado(String nombreUsuario, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

}
