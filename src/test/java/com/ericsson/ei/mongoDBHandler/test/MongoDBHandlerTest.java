package com.ericsson.ei.mongoDBHandler.test;

import org.junit.Before;
import org.junit.Test;

import com.ericsson.ei.mongodbhandler.MongoDBHandler;

import static org.junit.Assert.assertTrue;

public class MongoDBHandlerTest {
MongoDBHandler mongoDBHandler = new MongoDBHandler();
    
    String host = "localhost";
    int port = 27017;
    
    String dataBaseName = "EventStorageDBbbb";
    String collectionName = "SampleEvents";
    String input = "{\"id\":\"eventId\",\"type\":\"eventType11\",\"test_cases\" : [{\"event_id\" : \"testcaseid1\", \"test_data\" : \"testcase1data\"},{\"event_id\" : \"testcaseid2\", \"test_data\" : \"testcase2data\"}]}";
    String updateInput = "{\"id\":\"eventId\",\"type\":\"eventType11\",\"test_cases\" : [{\"event_id\" : \"testcaseid1\", \"test_data\" : \"testcase2data\"},{\"event_id\" : \"testcaseid3\", \"test_data\" : \"testcase3data\"}]}";
    String condition = "{\"test_cases.event_id\" : \"testcaseid1\"}";

    @Before
    public void init()
    {
        mongoDBHandler.createConnection(host,port);
    }

    @Test
    public void testInsertDocument(){
        assertTrue(mongoDBHandler.insertDocument(dataBaseName, collectionName, input));
        
    }

    @Test
    public void testGetDocuments(){
        assertTrue(mongoDBHandler.getAllDocuments(dataBaseName, collectionName));
    }

    @Test
    public void testGetDocumentOnCondition(){
        assertTrue(mongoDBHandler.getDocumentOnCondition(dataBaseName, collectionName, condition));
    }

    @Test
    public void testUpdateDocument(){
        assertTrue(mongoDBHandler.updateDocument(dataBaseName, collectionName, input, updateInput));
    }

    @Test
    public void testDropCollection(){
        assertTrue(mongoDBHandler.dropDocument(dataBaseName, collectionName, condition));
    }

}
