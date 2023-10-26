package com.unlam.tpi.infraestructura.helpers;

import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TrustAllCertificates {

	public static void confiarEnCertificado() {
		try {
			// Crear un SSLContext personalizado que desactive la verificación de
			// certificados
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { (TrustManager) new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} }, new java.security.SecureRandom());

			// Configurar la fábrica de sockets SSL para utilizar el SSLContext
			// personalizado
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

			// Ahora puedes abrir una conexión a la API .NET sin verificar los certificados
			// SSL
			// Escribe aquí tu código de conexión

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
