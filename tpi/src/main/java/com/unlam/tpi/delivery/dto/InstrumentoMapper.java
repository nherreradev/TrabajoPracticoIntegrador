package com.unlam.tpi.delivery.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.PanelesDePreciosConstantes;

public class InstrumentoMapper {

	public static List<Instrumento> convertirListaDeJsonAListaDeIntrumentos(String listaPreciosJson) {
		List<Instrumento> listaInstrumentos = new ArrayList<>();
		if (listaPreciosJson == null) {
			return listaInstrumentos;
		}
		Gson gson = new Gson();
		JsonObject jsonObject = JsonParser.parseString(listaPreciosJson).getAsJsonObject();
		JsonArray titulos = jsonObject.getAsJsonArray(PanelesDePreciosConstantes.TITULOS);
		for (int i = 0; i < titulos.size(); i++) {
			JsonObject jsonInstrumento = titulos.get(i).getAsJsonObject();
			Instrumento instrumento = gson.fromJson(jsonInstrumento, Instrumento.class);
			instrumento.setCategoriaInstrumento(PanelesDePreciosConstantes.ACCIONES);
			listaInstrumentos.add(instrumento);
		}
		return listaInstrumentos;
	}

}