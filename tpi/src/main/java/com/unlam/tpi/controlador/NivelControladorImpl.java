package com.unlam.tpi.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.model.Nivel;
import com.unlam.tpi.servicio.NivelServicio;

@RestController
@RequestMapping("/api/nivel")
public class NivelControladorImpl implements NivelControlador {
	
	@Autowired
	private NivelServicio nivelServicio;
	
	@Override
	@GetMapping("/guardar")
	public void guardar(Nivel nivel) {
		getNivelServicio().guardar(nivel);		
	}

	@Override
	@GetMapping("/obtener")
	public Nivel obtener(Long id) {
		return getNivelServicio().obtener(id);
	}

	@Override
	@GetMapping("/modificar")
	public Nivel modificar(Nivel nivel) {
		return getNivelServicio().modificar(nivel);
	}

	@Override
	@GetMapping("/borrar")
	public void borrar(Long id) {
		getNivelServicio().borrar(id);		
	}

	@Override
	@GetMapping("/listar")
	public List<Nivel> listar() {
		return getNivelServicio().listar();
	}

	public NivelServicio getNivelServicio() {
		return nivelServicio;
	}

	public void setNivelServicio(NivelServicio nivelServicio) {
		this.nivelServicio = nivelServicio;
	}

}
