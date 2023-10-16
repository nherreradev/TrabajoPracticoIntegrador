package com.unlam.tpi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.dto.CategoriaDTO;
import com.unlam.tpi.interfaces.CategoriaServicio;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaControladorImpl implements CategoriaControlador {

	@Autowired
	private CategoriaServicio categoriaServicio;
	
	@Override
	@PostMapping("/guardar")
	public void guardar(@RequestBody CategoriaDTO categoria) {
		getCategoriaServicio().guardar(categoria);
	}

	@Override
	@GetMapping("/obtener")
	public CategoriaDTO obtener(Long id) {
		return getCategoriaServicio().obtener(id);
	}

	@Override
	@GetMapping("/borrar")
	public void borrar(Long id) {
		getCategoriaServicio().borrar(id);		
	}

	@Override
	@GetMapping("/listar")
	public List<CategoriaDTO> listar() {
		return getCategoriaServicio().listar();
	}

	public CategoriaServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(CategoriaServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

}
