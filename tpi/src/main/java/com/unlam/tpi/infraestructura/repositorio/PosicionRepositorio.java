package com.unlam.tpi.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.Posicion;

public interface PosicionRepositorio extends JpaRepository<Posicion, Long>, PosicionRepositorioCustomizada {

}
