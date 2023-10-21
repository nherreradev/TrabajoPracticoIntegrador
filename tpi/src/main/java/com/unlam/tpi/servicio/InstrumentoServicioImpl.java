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
				+ "\"time\": \"2018-10-20\",\n" + "\"open\": 181.00,\n" + "\"high\": 182.50,\n" + "\"low\": 179.75,\n"
				+ "\"close\": 180.25\n" + "},\n" + "{\n" + "\"time\": \"2018-10-21\",\n" + "\"open\": 182.25,\n"
				+ "\"high\": 183.75,\n" + "\"low\": 181.50,\n" + "\"close\": 183.00\n" + "},\n" + "{\n"
				+ "\"time\": \"2018-10-22\",\n" + "\"open\": 183.50,\n" + "\"high\": 184.75,\n" + "\"low\": 182.00,\n"
				+ "\"close\": 184.25\n" + "},\n" + "{\n" + "\"time\": \"2018-10-23\",\n" + "\"open\": 184.00,\n"
				+ "\"high\": 185.50,\n" + "\"low\": 183.75,\n" + "\"close\": 185.25\n" + "},\n" + "{\n"
				+ "\"time\": \"2018-10-24\",\n" + "\"open\": 185.25,\n" + "\"high\": 186.75,\n" + "\"low\": 184.50,\n"
				+ "\"close\": 186.00\n" + "},\n" + "{\n" + "\"time\": \"2018-10-25\",\n" + "\"open\": 185.75,\n"
				+ "\"high\": 187.00,\n" + "\"low\": 185.25,\n" + "\"close\": 186.50\n" + "},\n" + "{\n"
				+ "\"time\": \"2018-10-26\",\n" + "\"open\": 186.75,\n" + "\"high\": 188.00,\n" + "\"low\": 186.00,\n"
				+ "\"close\": 187.25\n" + "},\n" + "{\n" + "\"time\": \"2018-10-27\",\n" + "\"open\": 187.50,\n"
				+ "\"high\": 188.50,\n" + "\"low\": 187.00,\n" + "\"close\": 188.00\n" + "},\n" + "{\n"
				+ "\"time\": \"2018-10-28\",\n" + "\"open\": 188.25,\n" + "\"high\": 189.00,\n" + "\"low\": 187.75,\n"
				+ "\"close\": 188.50\n" + "}\n" + "\n" + "]";

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
