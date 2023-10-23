package com.unlam.tpi.controlador;

import com.unlam.tpi.modelo.rest.FechaRequestHistorico;
import org.springframework.http.ResponseEntity;

public interface HistoricoControlador {
    ResponseEntity<String> GuardarHistorico(FechaRequestHistorico fechaRequestHistorico);
    ResponseEntity<String> GetHistorico(String rango, String instrumento);
}
