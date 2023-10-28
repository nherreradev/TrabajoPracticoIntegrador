package com.unlam.tpi.core.interfaces;

import java.util.List;

import com.unlam.tpi.infraestructura.modelo.HistoricoInstrumentoRespuesta;
import com.unlam.tpi.infraestructura.modelo.Instrumento;

public interface InstrumentoServicio {

	List<HistoricoInstrumentoRespuesta> getHistoricoInstrumento(String simbolo);

	void persistirInstrumentos(List<Instrumento> listaInstrumentos);

}
