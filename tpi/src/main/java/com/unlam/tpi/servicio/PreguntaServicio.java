package com.unlam.tpi.servicio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.dto.PreguntaDTO;
import com.unlam.tpi.modelo.persistente.Pregunta;

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
	public void cargaDesdeExcel(MultipartFile excelPregunta);

	@Transactional
	public PreguntaDTO getPreguntaDTOPorEnunciado(String nombre);

	@Transactional
	public Pregunta getPreguntaPorEnunciado(String pregunta);
}
