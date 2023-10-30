package com.unlam.tpi.core.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.Orden;

public interface OrdenRepositorio extends JpaRepository<Orden, Long> {

}
