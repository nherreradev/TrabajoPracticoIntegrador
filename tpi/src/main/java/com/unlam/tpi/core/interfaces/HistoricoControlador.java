package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

import com.unlam.tpi.core.modelo.FechaRequestHistorico;


public interface HistoricoControlador {
    ResponseEntity<String> guardarHistorico(FechaRequestHistorico fechaRequestHistorico);
}
