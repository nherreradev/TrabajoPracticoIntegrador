package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.core.modelo.Pregunta;
import com.unlam.tpi.delivery.dto.PreguntaDTO;

@Transactional
public interface PreguntaServicio {

	public void guardar(PreguntaDTO pregunta);

	public PreguntaDTO getPreguntaDTOPorID(Long id);

	public void borrar(Long id);

	public List<PreguntaDTO> listar();

	public List<PreguntaDTO> listarPorCategoria(String categoria);

	public void cargaDesdeExcel(MultipartFile excelPregunta) throws IOException;

	public PreguntaDTO getPreguntaDTOPorCodigo(String nombre);

	public Pregunta getPreguntaPorCodigo(String pregunta);

	void borrar(String codigo);

	public PreguntaDTO getPreguntaDTOPorEnunciado(String nombre);

	public Pregunta getPreguntaPorEnunciado(String enunciado);
}
