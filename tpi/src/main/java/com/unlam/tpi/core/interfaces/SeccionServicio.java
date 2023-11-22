package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.core.modelo.Seccion;


public interface SeccionServicio {

	@Transactional
	public void guardar(Seccion seccion);

	@Transactional
	public Seccion getSeccionDTOPorID(Long id);

	@Transactional
	public void borrar(Long id);

	@Transactional
	public List<Seccion> listar();

	@Transactional
	public void cargaDesdeExcel(MultipartFile excelSeccion) throws IOException;
	
	@Transactional
	public Seccion getSeccionPorNombre(String nombre);
}
