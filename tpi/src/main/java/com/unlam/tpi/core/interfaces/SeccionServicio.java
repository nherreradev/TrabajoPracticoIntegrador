package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.core.modelo.Seccion;
import com.unlam.tpi.delivery.dto.SeccionDTO;


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
	public void cargaDesdeExcel(MultipartFile excelSeccion) throws IOException;
	
	@Transactional
	public SeccionDTO getSeccionDTOPorNombre(String nombre);

	@Transactional
	public Seccion getSeccionPorNombre(String nombre);
}
