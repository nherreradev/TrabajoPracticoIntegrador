package com.unlam.tpi.infraestructura.repositorio;

import java.util.List;

public interface ListaPreciosRepository {
    void GuardarResponseTransaccion(String json, String collection);
    List<String> GetPriceList(String collection);
}
