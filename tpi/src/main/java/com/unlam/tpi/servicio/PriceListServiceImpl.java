package com.unlam.tpi.servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceListServiceImpl implements PriceListService{
    List<String> p = new ArrayList<>();
    private final RestTemplate restTemplate;

    public PriceListServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> GetPriceList(String titulo) {

        switch (titulo){
            case "bonos":
                break;
            case "acciones":
                break;
            default:
                break;
        }

        p.add("UNO");
        p.add("DOS");
        p.add("TRES");
        p.add("CUATRO");
        return p;
    }
}
