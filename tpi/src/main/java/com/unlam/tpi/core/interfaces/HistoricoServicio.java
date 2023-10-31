package com.unlam.tpi.core.interfaces;

import com.unlam.tpi.core.modelo.FechaRequestHistorico;

public interface HistoricoServicio {
    String GetHistoricoMongo(String rango, String instrumento);
    void GuardarHistorico(FechaRequestHistorico fechaRequestHistorico, String instrumento);
}
