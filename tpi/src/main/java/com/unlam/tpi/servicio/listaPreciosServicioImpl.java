package com.unlam.tpi.servicio;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class listaPreciosServicioImpl implements listaPreciosServicio{
    List<String> p = new ArrayList<>();
    private final RestTemplate restTemplate;
    public listaPreciosServicioImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> GetPriceList(String titulo, String token) {

        String response = this.restTemplate.getForObject("", String.class);
        switch (titulo) {
            case "bonos":
                break;
            case "acciones":
                break;
            default:
                break;
        }
        return null;
    }

}
