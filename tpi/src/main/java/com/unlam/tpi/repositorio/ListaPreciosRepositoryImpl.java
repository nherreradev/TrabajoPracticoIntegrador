package com.unlam.tpi.repositorio;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ListaPreciosRepositoryImpl implements ListaPreciosRepository{
    private final MongoTemplate mongoTemplate;

    public ListaPreciosRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public void GuardarResponseTransaccion(String json, String collection) {
        Document document = Document.parse(json);
        mongoTemplate.save(document, collection);
    }

    @Override
    public List<String> GetPriceList(String instrumento) {
        List<Document> documents = mongoTemplate.findAll(Document.class, instrumento);

        // Convierte los documentos a cadenas JSON
        List<String> jsonStrings = documents.stream()
                .map(Document::toJson)
                .collect(Collectors.toList());

        return jsonStrings;

        /*List<Document> documents = mongoTemplate.findAll(Document.class, instrumento);
        return null;*/
    }
}
