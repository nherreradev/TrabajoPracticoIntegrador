package com.unlam.tpi.repositorio;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
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
    public List<String> GetPriceList(String instrumento) throws IOException {
        List<Document> documents = mongoTemplate.findAll(Document.class, instrumento);
        for (Document doc : documents) {
            doc.remove("_id");
        }

        List<String> jsonStrings = documents.stream()
                .map(Document::toJson)
                .collect(Collectors.toList());
        return jsonStrings;
    }
}
