package com.unlam.tpi.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.Instrumento;

public interface InstrumentoRepositorio extends JpaRepository<Instrumento, Long>, InstrumentoRepositorioCustomizado {

	

}
