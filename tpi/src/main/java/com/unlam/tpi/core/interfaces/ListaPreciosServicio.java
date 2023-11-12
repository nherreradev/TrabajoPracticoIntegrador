package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ListaPreciosServicio {
    ResponseEntity<String> guardarListaPrecios(String titulo, String token);
    Map<String,Boolean> validateResponse(ResponseEntity<String> responseEntity, String instrumento);
    String getListaPrecioMongo(String instrumento);
}
