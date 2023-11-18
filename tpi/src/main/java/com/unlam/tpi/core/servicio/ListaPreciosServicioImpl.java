package com.unlam.tpi.core.servicio;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
	public ResponseEntity<String> guardarListaPrecios(String titulo, String token) throws JsonProcessingException {
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

	private void saveMongoTransaction(Map<String, Boolean> responseOK, ResponseEntity<String> responseEntity) throws JsonProcessingException {
		int IndexLlaveAbertura = responseEntity.toString().indexOf('{');
		int IndexLlaveCierre = responseEntity.toString().lastIndexOf('}');
		if (IndexLlaveAbertura != -1 && IndexLlaveCierre != -1 && IndexLlaveAbertura < IndexLlaveCierre) {
			String jsonToSave = responseEntity.toString().substring(IndexLlaveAbertura, IndexLlaveCierre + 1);
			String collection = getMapKey(responseOK);
			if(collection!=null) {
				if (responseOK.containsKey("cedears")){
					String save = limitarCantidadDeRegistrosCedears(jsonToSave);
					this.listaPreciosRepository.guardarResponseTransaccion(save, collection);
					return;
				}
				this.listaPreciosRepository.guardarResponseTransaccion(jsonToSave, collection);
			}
		}
	}

	private String limitarCantidadDeRegistrosCedears(String jsonToSave) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonToSave);
		JsonNode titulosNode = jsonNode.get("titulos");

		if (titulosNode.isArray() && titulosNode.size() > 60) {
			ArrayNode arrayNode = objectMapper.createArrayNode(); // Crear nuevo ArrayNode

			for (int i = 0; i < Math.min(60, titulosNode.size()); i++) {
				arrayNode.add((JsonNode) titulosNode.get(i));
			}

			// Asignar el nuevo ArrayNode a titulosNode
			((ObjectNode) jsonNode).replace("titulos", arrayNode);
		}

		jsonToSave = objectMapper.writeValueAsString(jsonNode);
		return jsonToSave;


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