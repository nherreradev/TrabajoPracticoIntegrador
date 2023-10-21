package com.unlam.tpi.repositorio;

import java.util.List;

public interface ListaPreciosRepository {
    void GuardarResponseTransaccion(String json, String collection);
    List<String> GetAllWithoutID(String collection);
}
