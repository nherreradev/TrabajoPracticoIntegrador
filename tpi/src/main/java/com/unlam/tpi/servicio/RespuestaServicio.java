package com.unlam.tpi.servicio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.dto.RespuestaDTO;

public interface RespuestaServicio {

	@Transactional
    public void guardar(RespuestaDTO respuesta);

    @Transactional
    public RespuestaDTO getRespuestaDTOPorID(Long id);

    @Transactional
    public void borrar(Long id);

    @Transactional
	public List<RespuestaDTO> listar();

    @Transactional
    public void cargaDesdeExcel(MultipartFile excelPregunta);
}
