package com.unlam.tpi.controlador;

import com.unlam.tpi.modelo.rest.FechaRequestHistorico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public interface HistoricoControlador {
    ResponseEntity<String> GuardarHistorico(@RequestBody FechaRequestHistorico fechaRequestHistorico);
    ResponseEntity<String> GetHistorico(String rango, String instrumento);
}
