package com.unlam.tpi.core.servicio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.PortafolioSugerenciaServicio;
import com.unlam.tpi.infraestructura.helpers.TrustAllCertificates;

@Service
public class PortafolioSugerenciaServicioImpl implements PortafolioSugerenciaServicio {

	@Value("${net.portafolio.url}")
	private String url_net;

	@Override
	public String obtenerRecomendacion(String tipoPerfil, int idProducto) throws IOException, KeyManagementException, NoSuchAlgorithmException {

		StringBuilder response = null;
		TrustAllCertificates.confiarEnCertificado();
		String url = url_net + tipoPerfil + "?idProducto=" + idProducto;
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
		}
		return response.toString();
	}

}
