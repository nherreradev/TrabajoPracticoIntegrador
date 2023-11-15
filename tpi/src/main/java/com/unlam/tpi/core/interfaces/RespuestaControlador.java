package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.delivery.dto.RespuestaDTO;

public interface RespuestaControlador {
	
    public void guardar(RespuestaDTO respuesta);

    public RespuestaDTO obtener(Long id);

    public void borrar(Long id);

	public List<RespuestaDTO> listar();

	public void cargaDesdeExcel(MultipartFile excelRespuesta)throws IOException;
}
