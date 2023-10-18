package com.unlam.tpi.controlador;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.dto.SeccionDTO;


public interface SeccionControlador {

    public void guardar(SeccionDTO categoria);

    public SeccionDTO obtener(Long id);

    public void borrar(Long id);

	public List<SeccionDTO> listar();

	public void cargaDesdeExcel(MultipartFile excelSeccion);
}
