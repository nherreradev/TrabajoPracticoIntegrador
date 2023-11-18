package com.unlam.tpi.infraestructura.repositorio;

import java.util.List;

public interface HistoricoRepositorio {
    List<String> getInstrumentoPorRangoFechaSinId(String rango, String instrumento);
    void guardarHistoricoInstrumento(String rango, String instrumento, String historico);
}
