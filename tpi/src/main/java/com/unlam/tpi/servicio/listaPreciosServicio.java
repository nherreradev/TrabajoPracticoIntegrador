package com.unlam.tpi.servicio;

import org.springframework.http.ResponseEntity;

public interface listaPreciosServicio {
    ResponseEntity<String> GetPriceList(String titulo, String token);
}
