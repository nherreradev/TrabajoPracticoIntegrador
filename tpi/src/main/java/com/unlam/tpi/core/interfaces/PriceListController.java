package com.unlam.tpi.core.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface PriceListController {
    public ResponseEntity<String> GuardarPrecios(@PathVariable String titulo) throws JsonProcessingException;
    public ResponseEntity<String> ObtenerPrecios(@PathVariable String titulo);
}
