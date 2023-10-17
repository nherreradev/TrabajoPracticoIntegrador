package com.unlam.tpi.controlador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.dto.CategoriaDTO;


public interface CategoriaControlador {

    public ResponseEntity<String> guardar(CategoriaDTO categoria);

    public CategoriaDTO obtener(Long id);

    public void borrar(Long id);

	public List<CategoriaDTO> listar();

	public void cargaDesdeExcel(MultipartFile excelCategoria);

}
