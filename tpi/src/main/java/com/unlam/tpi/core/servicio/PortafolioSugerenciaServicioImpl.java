package com.unlam.tpi.core.servicio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.unlam.tpi.infraestructura.arquitectura.ServiceException;
import com.unlam.tpi.infraestructura.helpers.TrustAllCertificates;

@Service
public class PortafolioSugerenciaServicioImpl implements PortafolioSugerenciaServicio {

	private final RestTemplate restTemplate;

	public PortafolioSugerenciaServicioImpl() {
		this.restTemplate = new RestTemplate();
	}

	@Override
	public String obtenerRecomendacion() {

		StringBuilder response = null;

		TrustAllCertificates.confiarEnCertificado();

		try {
			
		
		// URL de la API en ASP.NET Core
		String url = "https://localhost:7011/PortfolioRecomender"; // Asegúrate de reemplazar "puerto" con el
																	// puerto real en el que se está ejecutando tu
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.OK); // aplicación ASP.NET Core.

		// Crear una conexión HTTP
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

		// Establecer el método HTTP (GET en este caso)
		connection.setRequestMethod("GET");

		// Leer la respuesta
		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// La variable "response" contiene la respuesta JSON de la API en ASP.NET Core.
			System.out.println(response.toString());

		} else {
			System.out.println("Error en la solicitud. Código de respuesta: " + responseCode);
		}

		
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return response.toString();
	}

}
