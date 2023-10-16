package com.unlam.tpi.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.modelo.persistente.Pregunta;

public interface PreguntaRepositorio extends JpaRepository<Pregunta, Long>{

	public List<Pregunta> findByCategoria_Nombre(String categoria);
	
	public Pregunta findByEnunciado(String enunciado);

}
