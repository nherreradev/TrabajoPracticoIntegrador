package com.unlam.tpi.core.servicio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.delivery.dto.SeccionDTO;
import com.unlam.tpi.infraestructura.modelo.Seccion;


public interface SeccionServicio {

	@Transactional
	public void guardar(SeccionDTO seccion);

	@Transactional
	public SeccionDTO getSeccionDTOPorID(Long id);

	@Transactional
	public void borrar(Long id);

	@Transactional
	public List<SeccionDTO> listar();

	@Transactional
	public void cargaDesdeExcel(MultipartFile excelSeccion);
	
	@Transactional
	public SeccionDTO getSeccionDTOPorNombre(String nombre);

	@Transactional
	public Seccion getSeccionPorNombre(String nombre);
}
