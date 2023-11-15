package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.core.modelo.Pregunta;
import com.unlam.tpi.delivery.dto.PreguntaDTO;

public interface PreguntaServicio {

	@Transactional
    public void guardar(PreguntaDTO pregunta);

	@Transactional
    public PreguntaDTO getPreguntaDTOPorID(Long id);

	@Transactional
    public void borrar(Long id);

	@Transactional
	public List<PreguntaDTO> listar();

	@Transactional
	public List<PreguntaDTO> listarPorCategoria(String categoria);

	@Transactional
	public void cargaDesdeExcel(MultipartFile excelPregunta) throws IOException;

	@Transactional
	public PreguntaDTO getPreguntaDTOPorEnunciado(String nombre);

	@Transactional
	public Pregunta getPreguntaPorEnunciado(String pregunta);
}
