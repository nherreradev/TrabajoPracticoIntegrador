package com.unlam.tpi.delivery.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.PanelesDePreciosConstantes;

public class InstrumentoMapper {
	
	private static ModelMapper mapper = new ModelMapper();

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
	
	public static HistoricoInstrumentoDTO jsonAHistorico(JsonElement json) {
		return mapper.map(json, HistoricoInstrumentoDTO.class);
	}
	
	public static JsonArray armarHistoricoArray(String json) {
		Gson gson = new Gson();
		JsonArray jsonArray = gson.fromJson(json, JsonArray.class);

		JsonObject objetoLista = jsonArray.get(0).getAsJsonObject();

		JsonArray historicoArray = objetoLista.getAsJsonArray("historico");
		return historicoArray;
	}


}