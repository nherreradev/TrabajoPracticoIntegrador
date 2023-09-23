package com.unlam.tpi.repositorio;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

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
}
