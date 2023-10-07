package com.unlam.tpi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.dto.SeccionDTO;
import com.unlam.tpi.servicio.SeccionServicio;

@RestController
@RequestMapping("/api/seccion")
public class SeccionControladorImpl implements SeccionControlador {

	@Autowired
	private SeccionServicio categoriaServicio;
	
	@Override
	@PostMapping("/guardar")
	public void guardar(@RequestBody SeccionDTO categoria) {
		getCategoriaServicio().guardar(categoria);
	}

	@Override
	@GetMapping("/obtener")
	public SeccionDTO obtener(Long id) {
		return getCategoriaServicio().obtener(id);
	}

	@Override
	@GetMapping("/borrar")
	public void borrar(Long id) {
		getCategoriaServicio().borrar(id);		
	}

	@Override
	@GetMapping("/listar")
	public List<SeccionDTO> listar() {
		return getCategoriaServicio().listar();
	}

	public SeccionServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(SeccionServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

}
