package com.unlam.tpi.servicio;
import com.unlam.tpi.repositorio.ListaPreciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class listaPreciosServicioImpl implements listaPreciosServicio{
    private ListaPreciosRepository listaPreciosRepository;
    String ACCIONES = "https://api.invertironline.com/api/v2/Cotizaciones/todos/argentina/Todos?cotizacionInstrumentoModel.instrumento=acciones&cotizacionInstrumentoModel.pais=argentina";
    String BONOS = "https://api.invertironline.com/api/v2/Cotizaciones/todos/argentina/Todos?cotizacionInstrumentoModel.instrumento=titulosPublicos&cotizacionInstrumentoModel.pais=argentina";
    List<String> p = new ArrayList<>();
    private final RestTemplate restTemplate;
    @Autowired
    public listaPreciosServicioImpl(RestTemplate restTemplate, ListaPreciosRepository listaPreciosServicioImpl) {
        this.restTemplate = restTemplate;
        this.listaPreciosRepository = listaPreciosServicioImpl;
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
                int indexOfOpenBrace = responseEntity.toString().indexOf('{');

                int indexOfCloseBrace = responseEntity.toString().lastIndexOf('}');

                if (indexOfOpenBrace != -1 && indexOfCloseBrace != -1 && indexOfOpenBrace < indexOfCloseBrace) {
                    String jsonToSave = responseEntity.toString().substring(indexOfOpenBrace, indexOfCloseBrace + 1);
                    System.out.println("JSON OK");
                    System.out.println(jsonToSave);
                    this.listaPreciosRepository.GuardarResponseTransaccion(jsonToSave, "acciones");
                } else {
                    // Manejar el caso en el que no se encuentren '{' o '}' en la cadena, o están en el orden incorrecto
                    // Puede ser un error o una respuesta inesperada
                    System.out.println("No se encontró un JSON válido en la respuesta.");
                }
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
