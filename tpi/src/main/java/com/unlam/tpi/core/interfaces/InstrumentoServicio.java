package com.unlam.tpi.core.interfaces;

import java.util.List;

import com.unlam.tpi.core.modelo.HistoricoInstrumentoRespuesta;
import com.unlam.tpi.core.modelo.Instrumento;

public interface InstrumentoServicio {

	List<HistoricoInstrumentoRespuesta> getHistoricoInstrumento(String simbolo);

	void persistirInstrumentos(List<Instrumento> listaInstrumentos);

	List<Instrumento> obtenerInstrumentosAlAzar();

}
