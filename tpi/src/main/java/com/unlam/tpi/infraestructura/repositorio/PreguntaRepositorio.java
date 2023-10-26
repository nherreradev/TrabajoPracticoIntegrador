package com.unlam.tpi.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.infraestructura.modelo.Pregunta;

public interface PreguntaRepositorio extends JpaRepository<Pregunta, Long>{

	public List<Pregunta> findByCategoria_Nombre(String categoria);
	
	public Pregunta findByEnunciado(String enunciado);

}
