package com.unlam.tpi.servicio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.model.Nivel;
import com.unlam.tpi.repositorio.NivelRepositorio;

@Service
@Transactional
public class NivelServicioImpl implements NivelServicio {

	@Autowired
	NivelRepositorio nivelRepositorio;
	
	
	@Override
	public void guardar(Nivel nivel) {
		getNivelRepositorio().guardar(nivel);
		
	}

	@Override
	public Nivel obtener(Long id) {
		return getNivelRepositorio().obtener(id);
	}

	@Override
	public Nivel modificar(Nivel nivel) {
		return getNivelRepositorio().modificar(nivel);
	}

	@Override
	public void borrar(Long id) {
		getNivelRepositorio().borrar(id);
		
	}

	@Override
	public List<Nivel> listar() {
		return getNivelRepositorio().listar();
	}

	public NivelRepositorio getNivelRepositorio() {
		return nivelRepositorio;
	}

	public void setNivelRepositorio(NivelRepositorio nivelRepositorio) {
		this.nivelRepositorio = nivelRepositorio;
	}

}
