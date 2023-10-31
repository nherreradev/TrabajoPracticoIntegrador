package com.unlam.tpi.delivery.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.core.interfaces.RespuestaControlador;
import com.unlam.tpi.core.interfaces.RespuestaServicio;
import com.unlam.tpi.delivery.dto.RespuestaDTO;

@RestController
@RequestMapping("/api/respuesta")
public class RespuestaControladorImpl implements RespuestaControlador{

	@Autowired
	private RespuestaServicio respuestaServicio;
	
	@Override
	@PostMapping(value = "/guardar", consumes = "application/json", produces = "application/json")
	public void guardar(RespuestaDTO respuesta) {
		getRespuestaServicio().guardar(respuesta);		
	}
	
	@Override
	@RequestMapping(path = "/carga-respuesta-excel", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void cargaDesdeExcel(@RequestParam MultipartFile excelRespuesta) {
		getRespuestaServicio().cargaDesdeExcel(excelRespuesta);
	}

	@Override
	@GetMapping("/obtener")
	public RespuestaDTO obtener(Long id) {
		return getRespuestaServicio().getRespuestaDTOPorID(id);
	}

	@Override
	@GetMapping("/borrar")
	public void borrar(Long id) {
		getRespuestaServicio().borrar(id);		
	}

	@Override
	@GetMapping("/listar")
	public List<RespuestaDTO> listar() {
		return getRespuestaServicio().listar();
	}

	public RespuestaServicio getRespuestaServicio() {
		return respuestaServicio;
	}

	public void setRespuestaServicio(RespuestaServicio respuestaServicio) {
		this.respuestaServicio = respuestaServicio;
	}

}
