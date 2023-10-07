package com.unlam.tpi.repositorio;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;

public interface ListaPreciosRepository {
    void GuardarResponseTransaccion(String json, String collection);
    List<String> GetPriceList(String collection) throws IOException;
}
