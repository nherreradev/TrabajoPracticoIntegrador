package com.unlam.tpi.core.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ListaPreciosServicio {
    ResponseEntity<String> SavePriceList(String titulo, String token);
    Map<String,Boolean> ValidateResponse(ResponseEntity<String> responseEntity, String instrumento);
    String GetPriceListMongo(String instrumento);
}
