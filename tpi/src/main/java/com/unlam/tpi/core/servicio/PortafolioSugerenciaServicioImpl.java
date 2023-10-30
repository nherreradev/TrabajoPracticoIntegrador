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

import com.unlam.tpi.core.interfaces.PortafolioSugerenciaServicio;
import com.unlam.tpi.core.modelo.ServiceException;
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
			
		
		String url = "https://localhost:7011/PortfolioRecomender/RecomendarPortafolio"; 
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.OK); // aplicación ASP.NET Core.
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("GET");
		int responseCode = connection.getResponseCode();
	
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();


		} else {
			System.out.println("Error en la solicitud. Código de respuesta: " + responseCode);
		}

		
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return response.toString();
	}

}
