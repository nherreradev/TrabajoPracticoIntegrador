package com.unlam.tpi.delivery.controlador;

import com.unlam.tpi.core.modelo.FechaRequestHistorico;
import com.unlam.tpi.core.modelo.HistoricoRequestGET;
import org.springframework.http.ResponseEntity;

public interface HistoricoControlador {
    ResponseEntity<String> GuardarHistorico(FechaRequestHistorico fechaRequestHistorico);
    ResponseEntity<String> GetHistorico(HistoricoRequestGET historicoRequestGET);
}
