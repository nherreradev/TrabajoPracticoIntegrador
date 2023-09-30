package com.unlam.tpi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.model.Contenido;
import com.unlam.tpi.model.ContenidoDTO;
import com.unlam.tpi.servicio.ContenidoServicio;

@RestController
@RequestMapping("/api/contenido")
public class ContenidoControladorImpl implements ContenidoControlador {

	@Autowired
	private ContenidoServicio contenidoServicio;


	@Override
    @PostMapping(value = "/guardar", consumes = "application/json", produces = "application/json")
	public void guardar(@RequestBody ContenidoDTO Contenido) {
		getContenidoServicio().guardar(Contenido);		
	}

	@Override
	@GetMapping("/obtener")
	public ContenidoDTO obtener(Long id) {
		return getContenidoServicio().obtener(id);
	}

	@Override
	@PostMapping("/modificar")
	public ContenidoDTO modificar(@RequestBody ContenidoDTO Contenido) {
		return getContenidoServicio().modificar(Contenido);
	}

	@Override
	@GetMapping("/borrar")
	public void borrar(Long id) {
		getContenidoServicio().borrar(id);
	}

	@Override
	@GetMapping("/listar")
	public List<ContenidoDTO> listar() {
		return getContenidoServicio().listar();
	}
	
	public ContenidoServicio getContenidoServicio() {
		return contenidoServicio;
	}

	public void setContenidoServicio(ContenidoServicio contenidoServicio) {
		this.contenidoServicio = contenidoServicio;
	}

	
} 
