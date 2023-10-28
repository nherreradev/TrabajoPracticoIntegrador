package com.unlam.tpi.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.infraestructura.modelo.Orden;

public interface OrdenRepositorio extends JpaRepository<Orden, Long> {

}
