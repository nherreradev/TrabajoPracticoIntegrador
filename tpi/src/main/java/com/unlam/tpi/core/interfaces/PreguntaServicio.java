package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.core.modelo.Pregunta;

@Transactional
public interface PreguntaServicio {

	public void guardar(Pregunta pregunta);

	public Pregunta getPreguntaDTOPorID(Long id);

	public void borrar(Long id);

	public List<Pregunta> listar();

	public List<Pregunta> listarPorCategoria(String categoria);

	public void cargaDesdeExcel(MultipartFile excelPregunta) throws IOException;

	public Pregunta getPreguntaDTOPorCodigo(String nombre);

	public Pregunta getPreguntaPorCodigo(String pregunta);

	void borrar(String codigo);

	public Pregunta getPreguntaDTOPorEnunciado(String nombre);

	public Pregunta getPreguntaPorEnunciado(String enunciado);
}
