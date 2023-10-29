package com.unlam.tpi.infraestructura.repositorio;

import com.unlam.tpi.core.modelo.Instrumento;

public interface InstrumentoRepositorioCustomizado {

	Instrumento encontrarPorSimbolo(String simbolo);

}
