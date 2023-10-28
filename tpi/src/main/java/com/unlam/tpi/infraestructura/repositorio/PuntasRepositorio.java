package com.unlam.tpi.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.infraestructura.modelo.Puntas;

public interface PuntasRepositorio extends JpaRepository<Puntas, Long> {

}
