package com.unlam.tpi.core.interfaces;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.delivery.dto.RespuestaDTO;
import com.unlam.tpi.infraestructura.modelo.Respuesta;

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

    @Transactional
    public Respuesta getRespuestaPorNombre(String nombre);

    @Transactional
    public RespuestaDTO getRespuestaDTOPorNombre(String nombre);
}