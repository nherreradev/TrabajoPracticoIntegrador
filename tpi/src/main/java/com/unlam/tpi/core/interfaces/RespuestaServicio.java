package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.core.modelo.Respuesta;
import com.unlam.tpi.delivery.dto.RespuestaDTO;

@Transactional
public interface RespuestaServicio {

    public void guardar(RespuestaDTO respuesta);

    public RespuestaDTO getRespuestaDTOPorID(Long id);

    public void borrar(Long id);

	public List<RespuestaDTO> listar();

    public void cargaDesdeExcel(MultipartFile excelPregunta) throws IOException;

    public Respuesta getRespuestaPorCodigo(String nombre);

    public RespuestaDTO getRespuestaDTOPorCodigo(String nombre);

	void borrar(String codigo);

	public RespuestaDTO getRespuestaDTOPorNombre(String nombre);

	public Respuesta getRespuestaPorNombre(String nombre);
}
