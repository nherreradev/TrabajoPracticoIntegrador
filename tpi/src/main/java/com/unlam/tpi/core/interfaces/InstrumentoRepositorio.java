package com.unlam.tpi.core.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.Instrumento;

public interface InstrumentoRepositorio extends JpaRepository<Instrumento, Long>, InstrumentoRepositorioCustomizado {

	

}
