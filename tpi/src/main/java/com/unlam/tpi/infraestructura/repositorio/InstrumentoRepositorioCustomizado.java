package com.unlam.tpi.infraestructura.repositorio;

import com.unlam.tpi.infraestructura.modelo.Instrumento;

public interface InstrumentoRepositorioCustomizado {

	Instrumento encontrarPorSimbolo(String simbolo);

}
