package com.unlam.tpi.infraestructura.repositorio;

import java.util.List;

public interface HistoricoRepositorio {
    List<String> GetInstrumentoPorRangoFechaSinId(String rango, String instrumento);
    void GuardarHistoricoInstrumento(String rango, String instrumento, String historico);
}
