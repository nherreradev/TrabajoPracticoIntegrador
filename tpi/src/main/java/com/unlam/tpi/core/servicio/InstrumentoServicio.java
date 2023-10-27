package com.unlam.tpi.core.servicio;

import java.util.List;

import com.unlam.tpi.infraestructura.modelo.HistoricoInstrumentoRespuesta;

public interface InstrumentoServicio {

	List<HistoricoInstrumentoRespuesta> getHistoricoInstrumento(String simbolo);

}
