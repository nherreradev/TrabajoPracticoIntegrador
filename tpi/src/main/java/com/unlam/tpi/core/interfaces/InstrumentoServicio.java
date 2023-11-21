package com.unlam.tpi.core.interfaces;

import java.util.List;

import com.unlam.tpi.core.modelo.HistoricoInstrumento;
import com.unlam.tpi.core.modelo.Instrumento;

public interface InstrumentoServicio {

	List<HistoricoInstrumento> getHistoricoInstrumento(String simbolo);

	void persistirInstrumentos(List<Instrumento> listaInstrumentos);

	List<Instrumento> obtenerInstrumentosAlAzar();

	Instrumento obtenerInstrumentoPorID(Long coProductoID);

	Instrumento obtenerInstrumentoPorTipoPerfil(String tipoPerfil);

	Instrumento obtenerInstrumentoPorSimbolo(String simboloInstrumento);

}
