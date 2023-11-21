package com.unlam.tpi.core.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.HistoricoPrecioAPI;
import com.unlam.tpi.core.interfaces.HistoricoServicio;
import com.unlam.tpi.core.modelo.FechaRequestHistorico;
import com.unlam.tpi.core.modelo.HistoricoInstrumento;

@Service
public class HistoricoServicioImpl implements HistoricoServicio {

	@Autowired
	private HistoricoPrecioAPI historicoPrecioAPI;
	
	
	@Override
	public List<HistoricoInstrumento> getHistoricoMongo(String rango, String instrumento, String simbolo) {
		return historicoPrecioAPI.getHistoricoMongo(rango, instrumento, simbolo);
	}

	@Override
	public void guardarHistorico(FechaRequestHistorico fechaRequestHistorico, String instrumento) {
		historicoPrecioAPI.guardarHistorico(fechaRequestHistorico, instrumento);
	}

}
