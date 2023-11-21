package com.unlam.tpi.core.interfaces;

import java.util.List;

import javax.transaction.Transactional;

import com.unlam.tpi.core.modelo.FechaRequestHistorico;
import com.unlam.tpi.core.modelo.HistoricoInstrumento;

@Transactional
public interface HistoricoServicio{

	List<HistoricoInstrumento> getHistoricoMongo(String rango, String instrumento, String simbolo);

	void guardarHistorico(FechaRequestHistorico fechaRequestHistorico, String instrumento);
	
}
