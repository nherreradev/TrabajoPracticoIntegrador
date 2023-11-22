package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.core.modelo.Categoria;

@Transactional
public interface CategoriaServicio {

	public void guardar(Categoria categoria);

	public Categoria getCategoriaPorID(Long id);

	public void borrar(Long id);

	public List<Categoria> listar();

	public void cargaDesdeExcel(MultipartFile excelcategoria) throws IOException;
	
	public Categoria getCategoriaPorNombre(String nombre);
}
