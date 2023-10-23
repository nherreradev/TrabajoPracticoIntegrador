package com.unlam.tpi.interfaces;

import com.unlam.tpi.modelo.rest.FechaRequestHistorico;

public interface HistoricoServicio {
    String GetHistoricoMongo(String rango, String instrumento);
    void GuardarHistorico(FechaRequestHistorico fechaRequestHistorico, String instrumento);
}
