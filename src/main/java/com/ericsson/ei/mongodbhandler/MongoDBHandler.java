package com.ericsson.ei.mongodbhandler;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

public class MongoDBHandler {
    MongoClient mongoClient;

    //Establishing the connection to mongodb and creating a collection
    public  void createConnection(String host, int port){
        mongoClient = new MongoClient(host , port);
    }

    //Insert data into collection
    public  boolean insertDocument(String dataBaseName, String collectionName, String input){
        try {
            DB db = mongoClient.getDB(dataBaseName);
            DBCollection table = db.getCollection(collectionName);
            DBObject dbObjectInput = (DBObject) JSON.parse(input);
            WriteResult result = table.insert(dbObjectInput);
            if (result.wasAcknowledged()) {
                System.out.println("Inserted successfully");
                return result.wasAcknowledged();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //Retrieve entire data from  collection
    public boolean getAllDocuments(String dataBaseName, String collectionName){
        try{
            DB db = mongoClient.getDB(dataBaseName);
            DBCollection table = db.getCollection(collectionName);
            DBCursor cursor = table.find();
            if (cursor.count()!=0){
                int i = 1;
                while (cursor.hasNext()) {
                    System.out.println("Inserted Document: "+i);
                    System.out.println(cursor.next());
                    i++;
                }
                return true;
            }
            else{
                System.out.println("No documents found");
                return false;
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //Retrieve data from the collection based on condition
    public  boolean getDocumentOnCondition(String dataBaseName, String collectionName, String condition){
        try{
            DB db = mongoClient.getDB(dataBaseName);
            DBCollection table = db.getCollection(collectionName);
            DBObject dbObjectCondition = (DBObject)JSON.parse(condition);
            DBCursor conditionalCursor = table.find(dbObjectCondition);
            if (conditionalCursor.count()!=0){
                while(conditionalCursor.hasNext()) {
                    System.out.println(conditionalCursor.next());
                }
                return true;
            }
            else{
                System.out.println("No documents found with given condition");
                return false;
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //update the document in collection
    public  boolean updateDocument(String dataBaseName, String collectionName, String input, String updateInput ){
        try{
            boolean existing = getDocumentOnCondition(dataBaseName,collectionName,input);
            if(existing){
                DB db = mongoClient.getDB(dataBaseName);
                DBCollection table = db.getCollection(collectionName);
                DBObject dbObjectInput = (DBObject)JSON.parse(input);
                DBObject dbObjectUpdateInput = (DBObject)JSON.parse(updateInput);
                WriteResult result = table.update(dbObjectInput , dbObjectUpdateInput);
                return result.isUpdateOfExisting();
            }
            else{
                System.out.println("Provide valid input document that needs to get updated");
                return false;
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //drop the document in collection
    public  boolean dropDocument(String dataBaseName, String collectionName,String condition){
        try{
            DB db = mongoClient.getDB(dataBaseName);
            DBCollection table = db.getCollection(collectionName);
            DBObject dbObjectCondition = (DBObject)JSON.parse(condition);
            WriteResult result = table.remove(dbObjectCondition);
            if(result.getN()>0){
                return true;
            }
            else{
                System.out.println("No documents found to delete");
                return false;
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
