package com.unlam.tpi.infraestructura.repositorio;

import java.util.List;

import com.unlam.tpi.delivery.dto.HistoricoInstrumentoDTO;

public interface HistoricoRepositorio {
    List<String> getInstrumentoPorRangoFechaSinId(String rango, String instrumento);
    void guardarHistoricoInstrumento(String rango, String instrumento, List<HistoricoInstrumentoDTO> historico);
}
