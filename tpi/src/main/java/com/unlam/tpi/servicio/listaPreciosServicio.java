package com.unlam.tpi.servicio;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface listaPreciosServicio {
    ResponseEntity<String> GetPriceList(String titulo, String token);

    Map<String,Boolean> ValidateResponse(ResponseEntity<String> responseEntity, String instrumento);
}
