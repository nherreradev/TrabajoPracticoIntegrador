package com.unlam.tpi.infraestructura.repositorio;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoricoRepositorioImpl implements HistoricoRepositorio{
    private final MongoTemplate mongoTemplate;
    public HistoricoRepositorioImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public List<String> getInstrumentoPorRangoFechaSinId(String rango, String instrumento) {
        return null;
    }

    @Override
    public void guardarHistoricoInstrumento(String rango, String instrumento, String historico) {
        String collection = instrumento + "-" + rango;
        Document document = Document.parse(historico);
        mongoTemplate.save(document, collection);
    }

}
