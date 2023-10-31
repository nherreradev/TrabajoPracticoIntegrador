package com.unlam.tpi.core.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.Pregunta;

public interface PreguntaRepositorio extends JpaRepository<Pregunta, Long>{

	public List<Pregunta> findByCategoria_Nombre(String categoria);
	
	public Pregunta findByEnunciado(String enunciado);

}
