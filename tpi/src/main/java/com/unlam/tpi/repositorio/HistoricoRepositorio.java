package com.unlam.tpi.repositorio;

import java.util.List;

public interface HistoricoRepositorio {
    List<String> GetInstrumentoPorRangoFechaSinId(String rango, String instrumento);
}
