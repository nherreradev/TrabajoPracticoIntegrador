package com.unlam.tpi.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlam.tpi.model.Instrumento;
import com.unlam.tpi.model.PanelesDePreciosConstantes;

@Service
public class PanelesServiceImpl implements PanelesService {

	@Autowired
	PanelPrecios panelPrecios;

	private final RestTemplate restTemplate;

	public PanelesServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Map<String, Instrumento> getPanelDeAcciones() {

		ResponseEntity<String> responseEntity = postApiAcciones();

		try {

			List<Instrumento> listaInstrumentos = new ArrayList<>();
			Gson gson = new Gson();
			String json = responseEntity.getBody();
			JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
			JsonArray titulos = jsonObject.getAsJsonArray(PanelesDePreciosConstantes.TITULOS);

			for (int i = 0; i < titulos.size(); i++) {
				JsonObject jsonInstrumento = titulos.get(i).getAsJsonObject();
				Instrumento instrumento = gson.fromJson(jsonInstrumento, Instrumento.class);
				listaInstrumentos.add(instrumento);
			}

			panelPrecios.agregarInstrumentosAlPanelDeAcciones(listaInstrumentos);

			return PanelPreciosImpl.panelAcciones;

		} catch (Exception e) {
			throw e;
		}
	}

	private ResponseEntity<String> postApiAcciones() {
		String url = "https://4160cd26-9e79-438e-8137-2eb00d9da222.mock.pstmn.io/instrumentos";

		// Define el contenido del cuerpo de la solicitud POST (si es necesario)
		String requestBody = "{\"key\": \"value\"}"; // Reemplazar con los datos a enviar en el cuerpo

		// Configura las cabeceras de la solicitud
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); // Establece el tipo de contenido del cuerpo

		// Crea una entidad HTTP con el cuerpo y las cabeceras
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		// Realiza la solicitud POST y obtiene la respuesta
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
		return responseEntity;
	}
}
