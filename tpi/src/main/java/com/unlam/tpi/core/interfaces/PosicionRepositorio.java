package com.unlam.tpi.core.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.Posicion;

public interface PosicionRepositorio extends JpaRepository<Posicion, Long>, PosicionRepositorioCustomizada {

}
