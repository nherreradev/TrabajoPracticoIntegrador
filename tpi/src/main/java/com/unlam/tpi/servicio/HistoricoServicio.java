package com.unlam.tpi.servicio;

import com.unlam.tpi.modelo.rest.FechaRequestHistorico;
import org.springframework.web.bind.annotation.RequestBody;

public interface HistoricoServicio {
    String GetHistorico(String rango, String instrumento);
    void GuardarHistorico(FechaRequestHistorico fechaRequestHistorico);
}
