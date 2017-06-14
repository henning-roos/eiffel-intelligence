package com.ericsson.ei.mergeHandler;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class MergeHandler {

    MongoClient mongoClient;

    //Establishing the connection to mongodb and creating a collection
    public void createConnection(String host, int port){
        mongoClient = new MongoClient(host , port);
        System.out.println("connection created");
    }

    //Insert data into collection
    public void insertDocument(String dataBaseName, String collectionName, String input){
        DB db = mongoClient.getDB(dataBaseName);
        DBCollection table = db.getCollection(collectionName);
        DBObject dbObjectInput = (DBObject)JSON.parse(input);
        table.insert(dbObjectInput);
    }

    //Retrieve entire data from  collection
    public void getDocuments(String dataBaseName, String collectionName){
        DB db = mongoClient.getDB(dataBaseName);
        DBCollection table = db.getCollection(collectionName);
        DBCursor cursor = table.find();
        int i = 1;
        while (cursor.hasNext()) { 
           System.out.println("Inserted Document: "+i); 
           System.out.println(cursor.next()); 
           i++;
        }
    }

    //Retrieve data from the collection based on condition
    public void getDocumentOnCondition(String dataBaseName, String collectionName,String condition){
        DB db = mongoClient.getDB(dataBaseName);
        DBCollection table = db.getCollection(collectionName);
        DBObject dbObjectCondition = (DBObject)JSON.parse(condition);
        DBCursor conditionalCursor = table.find(dbObjectCondition);
        while(conditionalCursor.hasNext()) {
            System.out.println(conditionalCursor.next());
        }
    }

    //update the document in collection
    public void updateDocument(String dataBaseName, String collectionName, String input, String updateInput ){
        DB db = mongoClient.getDB(dataBaseName);
        DBCollection table = db.getCollection(collectionName);
        DBObject dbObjectInput = (DBObject)JSON.parse(input);
        DBObject dbObjectUpdateInput = (DBObject)JSON.parse(updateInput);
        table.update(dbObjectInput , dbObjectUpdateInput);
    }

    //drop the document in collection
    public void dropDocument(String dataBaseName, String collectionName,String condition){
        DB db = mongoClient.getDB(dataBaseName);
        DBCollection table = db.getCollection(collectionName);
        DBObject dbObjectCondition = (DBObject)JSON.parse(condition);
        table.remove(dbObjectCondition);
    }

    public void addNewObject(){

    }
    
    public void createNewAggregationObject(){

    }
    
    public void updateEventToObjectMapInMemoryDB(String object){
        
    }

    public void saveAggreagatedObjectToInMemoryDB(){

    }

}