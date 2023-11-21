package com.unlam.tpi.infraestructura.repositorio;

import java.util.HashSet;
import java.util.List;

import com.unlam.tpi.core.modelo.HistoricoInstrumento;
import com.unlam.tpi.delivery.dto.HistoricoInstrumentoDTO;

public interface HistoricoRepositorio {
	List<HistoricoInstrumento> getInstrumentoPorRangoFechaSinId(String rango, String instrumento, String simbolo);
	void guardarHistoricoInstrumento(String rango, String instrumento, HashSet<HistoricoInstrumentoDTO> historico);
}
