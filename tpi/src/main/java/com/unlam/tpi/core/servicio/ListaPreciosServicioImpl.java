package com.unlam.tpi.core.servicio;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.unlam.tpi.core.interfaces.ListaPreciosRepository;
import com.unlam.tpi.core.interfaces.ListaPreciosServicio;

@Service
public class ListaPreciosServicioImpl implements ListaPreciosServicio {
   
	 @Autowired
	    private ListaPreciosRepository listaPreciosRepository;
	
	private static final String CEDEARS_KEY = "cedears";
	private static final String BONOS_KEY = "bonos";
	private static final String ACCIONES_KEY = "acciones";
	
	Integer INDEX = 0;
   
    private final RestTemplate restTemplate;
    
    String ACCIONES = "https://api.invertironline.com/api/v2/Cotizaciones/todos/argentina/Todos?cotizacionInstrumentoModel.instrumento=acciones&cotizacionInstrumentoModel.pais=argentina";
    String BONOS = "https://api.invertironline.com/api/v2/Cotizaciones/todos/argentina/Todos?cotizacionInstrumentoModel.instrumento=titulosPublicos&cotizacionInstrumentoModel.pais=argentina";
    String CEDEARS = "https://api.invertironline.com/api/v2/Cotizaciones/todos/argentina/Todos?cotizacionInstrumentoModel.instrumento=cedears&cotizacionInstrumentoModel.pais=argentina";
    
    public ListaPreciosServicioImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

	@Override
	public ResponseEntity<String> guardarListaPrecios(String titulo, String token) {
		Map<String, Boolean> ResponseOK = new HashMap<>();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));
		RequestEntity<?> requestEntity;
		ResponseEntity<String> responseEntity = null;
		switch (titulo) {
		case BONOS_KEY:
			requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(BONOS));
			responseEntity = restTemplate.exchange(requestEntity, String.class);
			ResponseOK = this.validateResponse(responseEntity, BONOS_KEY);
			break;
		case ACCIONES_KEY:
			requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(ACCIONES));
			responseEntity = restTemplate.exchange(requestEntity, String.class);
			ResponseOK = this.validateResponse(responseEntity, ACCIONES_KEY);
			break;
		case CEDEARS_KEY:
			requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(CEDEARS));
			responseEntity = restTemplate.exchange(requestEntity, String.class);
			ResponseOK = this.validateResponse(responseEntity, CEDEARS_KEY);
			break;
		default:
			break;
		}
		saveMongoTransaction(ResponseOK, responseEntity);
		return responseEntity;
	}

    private void saveMongoTransaction(Map<String, Boolean> responseOK, ResponseEntity<String> responseEntity) {
        int IndexLlaveAbertura = responseEntity.toString().indexOf('{');
        int IndexLlaveCierre = responseEntity.toString().lastIndexOf('}');
        if (IndexLlaveAbertura != -1 && IndexLlaveCierre != -1 && IndexLlaveAbertura < IndexLlaveCierre) {
            String jsonToSave = responseEntity.toString().substring(IndexLlaveAbertura, IndexLlaveCierre + 1);
            String collection = getMapKey(responseOK);
            if(collection!=null) {
            	this.listaPreciosRepository.guardarResponseTransaccion(jsonToSave, collection);
            }
        }
    }

    @Override
    public Map<String, Boolean> validateResponse(ResponseEntity<String> responseEntity, String instrumento) {
        Map<String, Boolean> ResponseOk = new HashMap<>();
        ResponseOk.put(instrumento, isStatusCodeOk(responseEntity) ? true : false);
        return ResponseOk;
    }

	@Override
	public String getListaPrecioMongo(String instrumento) {
		String resultadoFinalJSON = null;
		List<String> res = null;
		res = this.listaPreciosRepository.getAllWithoutID(instrumento);
		if (INDEX < res.size()) {
			resultadoFinalJSON = res.get(INDEX);
			INDEX++;
		} else {
			INDEX = 0;
		}
		return resultadoFinalJSON;
	}

	private String getMapKey(Map<String, Boolean> responseOK) {
		if(responseOK.containsKey(ACCIONES_KEY)) {
			return ACCIONES_KEY;
		}
		if(responseOK.containsKey(BONOS_KEY)) {
			return BONOS_KEY;
		}
		if(responseOK.containsKey(CEDEARS_KEY)) {
			return CEDEARS_KEY;
		}
		return null;
	}

	private boolean isStatusCodeOk(ResponseEntity<String> responseEntity) {
		return responseEntity.getStatusCode() == HttpStatus.OK;
	}

}
