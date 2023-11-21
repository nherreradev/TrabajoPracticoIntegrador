package com.unlam.tpi.core.interfaces;

import java.util.List;

import com.unlam.tpi.core.modelo.FechaRequestHistorico;
import com.unlam.tpi.core.modelo.HistoricoInstrumento;

public interface HistoricoPrecioAPI {
	List<HistoricoInstrumento> getHistoricoMongo(String rango, String instrumento, String simbolo);

	void guardarHistorico(FechaRequestHistorico fechaRequestHistorico, String instrumento);
}
