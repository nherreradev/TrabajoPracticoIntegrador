package com.unlam.tpi.servicio;
import com.unlam.tpi.interfaces.ListaPreciosServicio;
import com.unlam.tpi.repositorio.ListaPreciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Instant;
import java.util.*;

@Service
public class ListaPreciosServicioImpl implements ListaPreciosServicio {
    @Autowired
    private ListaPreciosRepository listaPreciosRepository;
    private final RestTemplate restTemplate;
    String ACCIONES = "https://api.invertironline.com/api/v2/Cotizaciones/todos/argentina/Todos?cotizacionInstrumentoModel.instrumento=acciones&cotizacionInstrumentoModel.pais=argentina";
    String BONOS = "https://api.invertironline.com/api/v2/Cotizaciones/todos/argentina/Todos?cotizacionInstrumentoModel.instrumento=titulosPublicos&cotizacionInstrumentoModel.pais=argentina";

    @Autowired
    public ListaPreciosServicioImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<String> SavePriceList(String titulo, String token) {
        Map<String, Boolean> ResponseOK = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        RequestEntity<?> requestEntity;
        ResponseEntity<String> responseEntity = null;

        try{
            switch (titulo) {
                case "bonos":
                    requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(BONOS));
                    responseEntity = restTemplate.exchange(requestEntity, String.class);
                    ResponseOK = this.ValidateResponse(responseEntity, "bonos");
                    break;
                case "acciones":
                    requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(ACCIONES));
                    responseEntity = restTemplate.exchange(requestEntity, String.class);
                    ResponseOK = this.ValidateResponse(responseEntity, "acciones");
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            System.out.println("|HTTP ERROR| "+"Error en la llamada a la API, exception: "+ e);
            e.printStackTrace();
            return null;
        }
        SaveMongoTransaction(ResponseOK, responseEntity);
        return responseEntity;
    }

    @Override
    public void SaveHistorical(String instrumento, Date fecha_desde, Date fecha_hasta, String token) {
        Map<String, Boolean> ResponseOK = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        RequestEntity<?> requestEntity;
        ResponseEntity<String> responseEntity = null;
        //TODO: definir logica para obtener rango de fecha, si es mensual, trimestral o semestral
        try{
            switch (instrumento){
                case "bonos":
                    requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(BONOS));
                    responseEntity = restTemplate.exchange(requestEntity, String.class);
                    ResponseOK = this.ValidateResponse(responseEntity, "bonos");
                    break;
                case "acciones":
                    requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(ACCIONES));
                    responseEntity = restTemplate.exchange(requestEntity, String.class);
                    ResponseOK = this.ValidateResponse(responseEntity, "acciones");
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            System.out.println("|HTTP ERROR| "+"Error en la llamada a la API, exception: "+ e);
            e.printStackTrace();
        }
        SaveMongoTransaction(ResponseOK, responseEntity);
    }

    private void SaveMongoTransaction(Map<String, Boolean> responseOK, ResponseEntity<String> responseEntity) {
        int IndexLlaveAbertura = responseEntity.toString().indexOf('{');
        int IndexLlaveCierre = responseEntity.toString().lastIndexOf('}');
        if (IndexLlaveAbertura != -1 && IndexLlaveCierre != -1 && IndexLlaveAbertura < IndexLlaveCierre) {
            String jsonToSave = responseEntity.toString().substring(IndexLlaveAbertura, IndexLlaveCierre + 1);
            String collection = GetMapKey(responseOK);
            this.listaPreciosRepository.GuardarResponseTransaccion(jsonToSave, collection);
        }
    }

    @Override
    public Map<String, Boolean> ValidateResponse(ResponseEntity<String> responseEntity, String instrumento) {
        Map<String, Boolean> ResponseOk = new HashMap<>();
        ResponseOk.put(instrumento, IsStatusCodeOk(responseEntity) ? true : false);
        return ResponseOk;
    }

    @Override
    public String GetPriceListMongo(String instrumento) {
        String resultadoFinalJSON = null;
        List <String> res = null;
        Integer index = null;
        try{
            res = this.listaPreciosRepository.GetAllWithoutID(instrumento);
            index = DeterminarIndexRandomDelArray(res);
            resultadoFinalJSON = res.get(index);
        }catch (Exception e){
            System.out.println("Error al obtener información de mongo"+ e);
            e.printStackTrace();
            return null;
        }
        return resultadoFinalJSON;
    }

    //1 será un mes,
    //2 serán 3 meses
    //3 serán 6 meses
    @Override
    public String GetHistorical(Integer tiemopElegido) {
        List<String> response = null;
        Integer index = null;
        try{
            switch (tiemopElegido){
                case 1:
                    response = this.listaPreciosRepository.GetAllWithoutID("mensual");
                    index = DeterminarIndexRandomDelArray(response);
                    return response.get(index);
                case 2:
                    response = this.listaPreciosRepository.GetAllWithoutID("trimestral");
                    index = DeterminarIndexRandomDelArray(response);
                    return response.get(index);
                case 3:
                    response = this.listaPreciosRepository.GetAllWithoutID("semestral");
                    index = DeterminarIndexRandomDelArray(response);
                    return response.get(index);
            }
            return null;
        }catch (Exception e){
            System.out.println("Error al obtener información de mongo"+ e);
            e.printStackTrace();
            return null;
        }

    }

    private String GetMapKey(Map<String, Boolean> responseOK) { return responseOK.containsKey("acciones") ? "acciones" : "bonos"; }
    private boolean IsStatusCodeOk(ResponseEntity<String> responseEntity) { return responseEntity.getStatusCode() == HttpStatus.OK; }
    private Integer DeterminarIndexRandomDelArray(List<String> res) { return new Random().nextInt(res.size()); }

}
