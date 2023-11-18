package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.FechaRequestHistorico;

public interface HistoricoServicio {
    String getHistoricoMongo(String rango, String instrumento, String simbolo);
    void guardarHistorico(FechaRequestHistorico fechaRequestHistorico, String instrumento);
}
