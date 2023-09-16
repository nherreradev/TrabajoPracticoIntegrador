package com.unlam.tpi.servicio;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InstrumentoServicioImpl implements InstrumentoServicio{

	private final RestTemplate restTemplate;
	
    public InstrumentoServicioImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
	
	@Override
	public void obtenerInstrumentos() {
		
		ResponseEntity<String> responseEntity = realizarPostAUrl();

        String response = responseEntity.getBody();

        System.out.println("Respuesta del servidor: " + response);
		
	}

	private ResponseEntity<String> realizarPostAUrl() {
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
