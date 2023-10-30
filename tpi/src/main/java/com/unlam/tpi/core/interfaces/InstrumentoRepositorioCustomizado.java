package com.unlam.tpi.core.interfaces;

import java.util.List;

import com.unlam.tpi.core.modelo.Instrumento;

public interface InstrumentoRepositorioCustomizado {

	Instrumento encontrarPorSimbolo(String simbolo);
	
	List<Instrumento> obtenerInstrumentosAlAzar();
	
	Instrumento obtenerInstrumentoPorID(Long coProductoID);

}
