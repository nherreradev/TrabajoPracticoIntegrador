package com.unlam.tpi.core.interfaces;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.core.modelo.Categoria;
import com.unlam.tpi.delivery.dto.CategoriaDTO;

public interface CategoriaServicio {

	@Transactional
	public void guardar(CategoriaDTO categoria);

	@Transactional
	public CategoriaDTO getCategoriaDTOPorID(Long id);

	@Transactional
	public void borrar(Long id);

	@Transactional
	public List<CategoriaDTO> listar();

	@Transactional
	public void cargaDesdeExcel(MultipartFile excelcategoria);
	
	@Transactional
	public CategoriaDTO getCategoriaDTOPorNombre(String nombre);

	@Transactional
	public Categoria getCategoriaPorNombre(String nombre);
}
