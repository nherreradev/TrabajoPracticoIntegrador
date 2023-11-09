package com.unlam.tpi.core.interfaces;

import java.util.List;

public interface ListaPreciosRepository {
    void guardarResponseTransaccion(String json, String collection);
    List<String> getAllWithoutID(String collection);

}
