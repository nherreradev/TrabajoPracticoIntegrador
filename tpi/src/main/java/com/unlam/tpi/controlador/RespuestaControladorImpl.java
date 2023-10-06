package com.unlam.tpi.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.dto.RespuestaDTO;
import com.unlam.tpi.servicio.RespuestaServicio;

@RestController
@RequestMapping("/api/respuesta")
public class RespuestaControladorImpl implements RespuestaControlador{

	private RespuestaServicio respuestaServicio;
	
	@Override
	@PostMapping(value = "/guardar", consumes = "application/json", produces = "application/json")
	public void guardar(RespuestaDTO respuesta) {
		getRespuestaServicio().guardar(respuesta);		
	}

	@Override
	@GetMapping("/obtener")
	public RespuestaDTO obtener(Long id) {
		return getRespuestaServicio().obtener(id);
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
