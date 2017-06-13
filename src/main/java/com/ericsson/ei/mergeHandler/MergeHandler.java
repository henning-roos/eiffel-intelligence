package com.ericsson.ei.mergeHandler;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class MergeHandler {
    
    public void addNewObject(){
        
    }
    
    public void createNewAggregationObject(){
        
    }
    
    public static void main(String args[]){
        String input = "{\"id\":\"eventId\",\"type\":\"eventType11\",\"test_cases\" : [{\"event_id\" : \"testcaseid1\", \"test_data\" : \"testcase1data\"},{\"event_id\" : \"testcaseid2\", \"test_data\" : \"testcase2data\"}]}";
        updateEventToObjectMapInMemoryDB(input);
    }
    
    public static void updateEventToObjectMapInMemoryDB(String object){
        try {
            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("EventStorageDB");
            DBCollection table = db.getCollection("SampleEvents");
            DBObject dbObject = (DBObject)JSON.parse(object);
            table.insert(dbObject);
        }
        catch (Exception e) {
            e.getMessage();
        }
    }
    
    public void saveAggreagatedObjectToInMemoryDB(){
        
    }

}
