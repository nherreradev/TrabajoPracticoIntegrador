package com.unlam.tpi.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface PriceListController {
    public ResponseEntity<String> GuardarPrecios(@PathVariable String titulo);
    public ResponseEntity<String> ObtenerPrecios(@PathVariable String titulo);
}
