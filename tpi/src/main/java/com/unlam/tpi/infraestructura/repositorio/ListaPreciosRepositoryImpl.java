package com.unlam.tpi.infraestructura.repositorio;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.unlam.tpi.core.interfaces.ListaPreciosRepository;

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
    public List<String> GetAllWithoutID(String instrumento) {
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
