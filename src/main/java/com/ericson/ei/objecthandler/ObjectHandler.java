package com.ericson.ei.objecthandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ObjectHandler {
    @Value("${aggregated.collection.name}") private String collectionName;

    public void insertObject(String aggregatedObject) {

    }

    public void findObjectById(String condition) {

    }
}
