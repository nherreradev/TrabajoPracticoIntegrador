package com.unlam.tpi.core.interfaces;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.core.modelo.Respuesta;

@Transactional
public interface RespuestaServicio {

    public void guardar(Respuesta respuesta);

    public void borrar(Long id);

	public List<Respuesta> listar();

    public void cargaDesdeExcel(MultipartFile excelPregunta) throws IOException;

    public Respuesta getRespuestaPorCodigo(String nombre);

	void borrar(String codigo);

	public Respuesta getRespuestaPorNombre(String nombre);
	
	public Respuesta getRespuestaPorID(Long id);
	
}
