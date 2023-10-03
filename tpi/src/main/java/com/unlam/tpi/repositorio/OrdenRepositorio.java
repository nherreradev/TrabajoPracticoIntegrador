package com.unlam.tpi.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.modelo.persistente.Orden;

public interface OrdenRepositorio extends JpaRepository<Orden, Long> {

}
