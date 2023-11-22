package com.unlam.tpi.infraestructura.repositorio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlam.tpi.core.modelo.HistoricoInstrumento;
import com.unlam.tpi.delivery.dto.HistoricoInstrumentoDTO;
import com.unlam.tpi.delivery.dto.InstrumentoMapper;

@Repository
public class HistoricoRepositorioImpl implements HistoricoRepositorio {
	private final MongoTemplate mongoTemplate;

	public HistoricoRepositorioImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<HistoricoInstrumento> getInstrumentoPorRangoFechaSinId(String rango, String instrumento,
			String simbolo) {
		String collectionNombre = instrumento + "-" + rango;
		Criteria criteria = Criteria.where("historico.simbolo").is(simbolo);
		Query query = new Query(criteria);
		List<Document> result = mongoTemplate.find(query, Document.class, collectionNombre);
		for (Document doc : result) {
			doc.remove("_id");
		}
		List<String> jsonStrings = result.stream().map(Document::toJson).collect(Collectors.toList());
		return crearListaHistoricos(jsonStrings.toString());
	}

	private List<HistoricoInstrumento> crearListaHistoricos(String json) {
		JsonArray historicoArray = InstrumentoMapper.armarHistoricoArray(json);
		List<HistoricoInstrumento> listaHistoricoInstrumentoRespuesta = new ArrayList<>();
		for (JsonElement elemento : historicoArray) {
			HistoricoInstrumento historicoInstrumentoRespuesta = crearHistoricoInstrumentoRespuesta(elemento);
			listaHistoricoInstrumentoRespuesta.add(historicoInstrumentoRespuesta);
		}
		return listaHistoricoInstrumentoRespuesta;
	}

	private HistoricoInstrumento crearHistoricoInstrumentoRespuesta(JsonElement elemento) {
		HistoricoInstrumento historicoInstrumentoRespuesta = new HistoricoInstrumento();
		JsonObject objeto = elemento.getAsJsonObject();
		String tiempo = objeto.get("fecha").getAsString();
		String precioDeApertura = objeto.get("apertura").getAsString();
		String maximo = objeto.get("maximo").getAsString();
		String minimo = objeto.get("minimo").getAsString();
		String precioDeCierre = objeto.get("cierre").getAsString();
		historicoInstrumentoRespuesta = new HistoricoInstrumento();
		historicoInstrumentoRespuesta.setTiempo(tiempo);
		historicoInstrumentoRespuesta.setPrecioDeApertura(precioDeApertura);
		historicoInstrumentoRespuesta.setMaximo(maximo);
		historicoInstrumentoRespuesta.setMinimo(minimo);
		historicoInstrumentoRespuesta.setPrecioDeCierre(precioDeCierre);
		return historicoInstrumentoRespuesta;
	}

	@Override
	public void guardarHistoricoInstrumento(String rango, String instrumento,
			HashSet<HistoricoInstrumentoDTO> historico) {
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
