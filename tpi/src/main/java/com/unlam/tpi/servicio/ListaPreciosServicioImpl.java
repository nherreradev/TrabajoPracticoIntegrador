package com.unlam.tpi.servicio;
import com.unlam.tpi.repositorio.ListaPreciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private void SaveMongoTransaction(Map<String, Boolean> responseOK, ResponseEntity<String> responseEntity) {
        int IndexLlaveAbertura = responseEntity.toString().indexOf('{');
        int IndexLlaveCierre = responseEntity.toString().lastIndexOf('}');
        if (IndexLlaveAbertura != -1 && IndexLlaveCierre != -1 && IndexLlaveAbertura < IndexLlaveCierre) {
            String jsonToSave = responseEntity.toString().substring(IndexLlaveAbertura, IndexLlaveCierre + 1);
            String collection = GetMapKey(responseOK);
            this.listaPreciosRepository.GuardarResponseTransaccion(jsonToSave, collection);
        }
    }

    private String GetMapKey(Map<String, Boolean> responseOK) {
        if (responseOK.containsKey("acciones")){
            return "acciones";
        }
        return "bonos";
    }


    @Override
    public Map<String, Boolean> ValidateResponse(ResponseEntity<String> responseEntity, String instrumento) {
        Map<String, Boolean> ResponseOk = new HashMap<>();
        ResponseOk.put(instrumento, IsStatusCodeOk(responseEntity) ? true : false);
        return ResponseOk;
    }

    private boolean IsStatusCodeOk(ResponseEntity<String> responseEntity) { return responseEntity.getStatusCode() == HttpStatus.OK; }

    @Override
    public List <String> GetPriceListMongo(String instrumento) {
        List <String> res = null;
        try{
            res = this.listaPreciosRepository.GetPriceList(instrumento);
        }catch (Exception e){
            System.out.println("Error al obtener información de mongo"+ e);
            e.printStackTrace();
            return null;
        }
        return res;
    }
        /*try{
            String res = this.listaPreciosRepository.GetPriceList(instrumento);
        }catch (Exception e){
            System.out.println("Error al obtener información de mongo"+ e);
            e.printStackTrace();
            return null;
        }
        return res;
    }*/

}
