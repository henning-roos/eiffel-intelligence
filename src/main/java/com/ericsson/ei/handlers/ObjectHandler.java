package com.ericsson.ei.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ericsson.ei.mongodbhandler.MongoDBHandler;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class ObjectHandler {
    @Value("${aggregated.collection.name}") private String collectionName;
    @Value("${database.name}") private String databaseName;

    @Autowired
    private MongoDBHandler mongoDbHandler;

    public void insertObject(String aggregatedObject) {

    }

    public void insertObject(JsonNode aggregatedObject) {
        insertObject(aggregatedObject.asText());
    }

    public String findObjectById(String condition) {
        return "";
        //return mongoDbHandler.getDocumentOnCondition(databaseName, collectionName, condition);
    }
}
