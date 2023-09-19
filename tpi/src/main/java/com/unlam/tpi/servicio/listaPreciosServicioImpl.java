package com.unlam.tpi.servicio;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class listaPreciosServicioImpl implements listaPreciosServicio{
    String ACCIONES = "https://api.invertironline.com/api/v2/Cotizaciones/todos/argentina/Todos?cotizacionInstrumentoModel.instrumento=acciones&cotizacionInstrumentoModel.pais=argentina";
    String BONOS = "https://api.invertironline.com/api/v2/Cotizaciones/todos/argentina/Todos?cotizacionInstrumentoModel.instrumento=titulosPublicos&cotizacionInstrumentoModel.pais=argentina";
    List<String> p = new ArrayList<>();
    private final RestTemplate restTemplate;
    public listaPreciosServicioImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<String> GetPriceList(String titulo, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        RequestEntity<?> requestEntity;
        ResponseEntity<String> responseEntity = null;

        switch (titulo) {
            case "bonos":
                requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(BONOS));
                responseEntity = restTemplate.exchange(requestEntity, String.class);
                break;
            case "acciones":
                requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(ACCIONES));
                responseEntity = restTemplate.exchange(requestEntity, String.class);
                break;
            default:
                break;
        }

        switch (responseEntity.getStatusCode()){
            case OK:
                System.out.println("AUTORIZADO");
                break;
            case BAD_REQUEST:
                System.out.println("BAD REQUEST");
                break;
            case UNAUTHORIZED:
                System.out.println("UNAUTHORIZED");
                break;
            default:
                break;
        }

        return responseEntity;
    }

}
