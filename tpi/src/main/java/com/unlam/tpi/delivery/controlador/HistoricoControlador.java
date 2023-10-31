package com.unlam.tpi.delivery.controlador;

import com.unlam.tpi.core.modelo.FechaRequestHistorico;
import org.springframework.http.ResponseEntity;

public interface HistoricoControlador {
    ResponseEntity<String> GuardarHistorico(FechaRequestHistorico fechaRequestHistorico);
    ResponseEntity<String> GetHistorico(String rango, String instrumento);
}
