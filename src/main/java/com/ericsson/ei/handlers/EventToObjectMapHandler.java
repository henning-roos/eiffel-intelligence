package com.ericsson.ei.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ericsson.ei.jmespath.JmesPathInterface;
import com.ericsson.ei.mongodbhandler.MongoDBHandler;
import com.ericsson.ei.rules.RulesHandler;
import com.ericsson.ei.rules.RulesObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author evasiba
 * Class for handling event to object map.
 * The map has the event id as key and the value is a list
 * with all the ids of objects that an event has contributed to.
 *
 */
@Component
public class EventToObjectMapHandler {

    static Logger log = (Logger) LoggerFactory.getLogger(ExtractionHandler.class);

    @Value("${event_object_map.collection.name}") private String collectionName;
    @Value("${database.name}") private String databaseName;

    @Autowired
    MongoDBHandler mongodbhandler;

    @Autowired
    JmesPathInterface jmesPathInterface;

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setMongodbhandler(MongoDBHandler mongodbhandler) {
        this.mongodbhandler = mongodbhandler;
    }

    public void setJmesPathInterface(JmesPathInterface jmesPathInterface) {
        this.jmesPathInterface = jmesPathInterface;
    }

    public ArrayList<String> getObjectsForEvent(RulesObject rulesObject, String event) {
        String eventId = getEventId(rulesObject, event);
        Map<String, ArrayList<String>> map =  getEventToObjectMap(eventId);
        return map.get(eventId);
    }

    public ArrayList<String> getObjectsForEventId(String eventId) {
        Map<String, ArrayList<String>> map =  getEventToObjectMap(eventId);
        return map.get(eventId);
    }

    public void updateEventToObjectMapInMemoryDB(RulesObject rulesObject, String event, String objectId) {
        String eventId = getEventId(rulesObject, event);
        Map<String, ArrayList<String>> map =  getEventToObjectMap(eventId);
        map = updateMap(map, eventId, objectId);

    }

    public String getEventId(RulesObject rulesObject, String event) {
        String idRule = rulesObject.getIdRule();
        JsonNode eventIdJson = jmesPathInterface.runRuleOnEvent(idRule, event);
        return eventIdJson.toString();
    }

    public Map<String, ArrayList<String>> updateMap(Map<String, ArrayList<String>> map, String eventId, String objectId) {
        ArrayList<String> objectIds = map.get(eventId);
        if (objectIds == null) {
            objectIds = new ArrayList<String>();
        }
        objectIds.add(objectId);
        map.put(eventId, objectIds);
        return map;
    }

    public Map<String, ArrayList<String>> getEventToObjectMap(String eventId) {
        Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        String condition = "{\"_id\" : \"" + eventId + "\"}";
        ArrayList<String> documents = mongodbhandler.find(databaseName, collectionName, condition);
        String mapStr = documents.get(0);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode mapJson = mapper.readValue(mapStr, JsonNode.class);
            JsonNode value = mapJson.get(eventId);
            ArrayList<String> idList = new ObjectMapper().readValue(value.traverse(), new TypeReference<ArrayList<String>>(){});
            map.put(eventId, idList);
        } catch (Exception e) {
            log.info(e.getMessage(),e);
        }
        return map;
    }


}
