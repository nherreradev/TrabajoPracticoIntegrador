package com.unlam.tpi.servicio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceListServiceImpl implements PriceListService{
    List<String> p = new ArrayList<>();

    @Override
    public List<String> GetPriceList() {
        p.add("UNO");
        p.add("DOS");
        p.add("TRES");
        p.add("CUATRO");
        return p;
    }
}
