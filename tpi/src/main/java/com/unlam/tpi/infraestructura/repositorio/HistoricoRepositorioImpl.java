package com.unlam.tpi.infraestructura.repositorio;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.delivery.dto.HistoricoInstrumentoDTO;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HistoricoRepositorioImpl implements HistoricoRepositorio {
	private final MongoTemplate mongoTemplate;

	public HistoricoRepositorioImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<String> getInstrumentoPorRangoFechaSinId(String rango, String instrumento) {
		String collection = instrumento + "-" + rango;
		List<Document> documents = mongoTemplate.findAll(Document.class, collection);
		for (Document doc : documents) {
			doc.remove("_id");
		}
		List<String> jsonStrings = documents.stream().map(Document::toJson).collect(Collectors.toList());
		return jsonStrings;

	}

	@Override
	public void guardarHistoricoInstrumento(String rango, String instrumento, List<HistoricoInstrumentoDTO> historico) {

		Gson gson = new Gson();
		String json = gson.toJson(historico);

		JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

		JsonObject listaCompleta = new JsonObject();
		listaCompleta.add("historico", jsonArray);

		String jsonCompleto = listaCompleta.toString();

		String collection = instrumento + "-" + rango;
		Document document = Document.parse(jsonCompleto);
		mongoTemplate.save(document, collection);
		
	}

}
