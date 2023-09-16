package com.unlam.tpi.servicio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class listaPreciosServicioImpl implements listaPreciosServicio{
    List<String> p = new ArrayList<>();

    @Override
    public List<String> getListaPrecios() {
        p.add("UNO");
        p.add("DOS");
        p.add("TRES");
        p.add("CUATRO");
        return p;
    }
}
