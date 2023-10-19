package com.unlam.tpi.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlam.tpi.interfaces.InstrumentoServicio;
import com.unlam.tpi.modelo.rest.HistoricoInstrumentoRespuesta;

@Service
public class InstrumentoServicioImpl implements InstrumentoServicio {

	@Override
	public List<HistoricoInstrumentoRespuesta> getHistoricoInstrumento(String simbolo) {
		
		List<HistoricoInstrumentoRespuesta> listaHistoricoInstrumentoRespuesta = new ArrayList<>();

		String json = "[\n" + "\n" + "{\n" + "\"time\": \"2018-10-19\",\n" + "\"open\": 180.34,\n"
				+ "\"high\": 180.99,\n" + "\"low\": 178.57,\n" + "\"close\": 179.85\n" + "},\n" + "\n" + "{\n"
				+ "\"time\": \"2018-10-19\",\n" + "\"open\": 180.34,\n" + "\"high\": 180.99,\n" + "\"low\": 178.57,\n"
				+ "\"close\": 179.85\n" + "}\n" + "\n" + "]";

		JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

		for (JsonElement elemento : jsonArray) {
			HistoricoInstrumentoRespuesta historicoInstrumentoRespuesta = new HistoricoInstrumentoRespuesta();
			JsonObject objeto = elemento.getAsJsonObject();

			String tiempo = objeto.get("time").getAsString();
			String precioDeApertura = objeto.get("open").getAsString();
			String maximo = objeto.get("high").getAsString();
			String minimo = objeto.get("low").getAsString();
			String precioDeCierre = objeto.get("close").getAsString();

			historicoInstrumentoRespuesta = new HistoricoInstrumentoRespuesta();
			historicoInstrumentoRespuesta.setTiempo(tiempo);
			historicoInstrumentoRespuesta.setPrecioDeApertura(precioDeApertura);
			historicoInstrumentoRespuesta.setMaximo(maximo);
			historicoInstrumentoRespuesta.setMinimo(minimo);
			historicoInstrumentoRespuesta.setPrecioDeCierre(precioDeCierre);
			
			listaHistoricoInstrumentoRespuesta.add(historicoInstrumentoRespuesta);
			
		}

		return listaHistoricoInstrumentoRespuesta;
	}

	/*
	 * { time: "2018-10-19", open: 180.34, high: 180.99, low: 178.57, close: 179.85,
	 * }
	 */

}
