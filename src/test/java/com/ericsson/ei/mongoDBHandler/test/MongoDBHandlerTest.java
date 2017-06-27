package com.ericsson.ei.mongoDBHandler.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ericsson.ei.mongodbhandler.MongoDBHandler;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;

public class MongoDBHandlerTest {

    static MongoDBHandler mongoDBHandler = new MongoDBHandler();

    static String host = "localhost";
    static int port = 27017;

    static String dataBaseName = "EventStorageDBbbb";
    static String collectionName = "SampleEvents";
    static String input = "{\"id\":\"eventId\",\"type\":\"eventType11\",\"test_cases\" : [{\"event_id\" : \"testcaseid1\", \"test_data\" : \"testcase1data\"},{\"event_id\" : \"testcaseid2\", \"test_data\" : \"testcase2data\"}]}";
    String updateInput = "{\"id\":\"eventId\",\"type\":\"eventType11\",\"test_cases\" : [{\"event_id\" : \"testcaseid1\", \"test_data\" : \"testcase2data\"},{\"event_id\" : \"testcaseid3\", \"test_data\" : \"testcase3data\"}]}";
    static String condition = "{\"test_cases.event_id\" : \"testcaseid1\"}";

    @BeforeClass
    public static void init()
    {
        mongoDBHandler.createConnection(host,port);
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

    @AfterClass
    public static void dropCollection()
    {
        assertTrue(mongoDBHandler.dropDocument(dataBaseName, collectionName, condition));
    }
}
