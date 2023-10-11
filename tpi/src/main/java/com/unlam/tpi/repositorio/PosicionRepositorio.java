package com.unlam.tpi.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.modelo.persistente.Posicion;

public interface PosicionRepositorio extends JpaRepository<Posicion, Long>, PosicionRepositorioCustomizada {

	

}
