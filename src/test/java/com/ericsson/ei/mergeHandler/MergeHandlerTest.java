package com.ericsson.ei.mergeHandler;

import org.junit.Before;
import org.junit.Test;

public class MergeHandlerTest {
    
    MergeHandler mergeHandler;
    
    String host = "localhost";
    int port = 27017;
    
    String dataBaseName = "EventStorageDB";
    String collectionName = "SampleEvents";
    String input = "{\"id\":\"eventId\",\"type\":\"eventType11\",\"test_cases\" : [{\"event_id\" : \"testcaseid1\", \"test_data\" : \"testcase1data\"},{\"event_id\" : \"testcaseid2\", \"test_data\" : \"testcase2data\"}]}";
    String updateInput = "{\"id\":\"eventId\",\"type\":\"eventType11\",\"test_cases\" : [{\"event_id\" : \"testcaseid2\", \"test_data\" : \"testcase2data\"},{\"event_id\" : \"testcaseid3\", \"test_data\" : \"testcase3data\"}]}";
    String condition = "{\"test_cases.event_id\" : \"testcaseid1\"}";

    @Before
    public void init()
    {
        mergeHandler.createConnection(host,port);
    }

    @Test
    public void testInsertDocument(){
        mergeHandler.insertDocument(dataBaseName, collectionName, input);
        
    }

    @Test
    public void testGetDocuments(){
        mergeHandler.getDocuments(dataBaseName, collectionName);
    }

    @Test
    public void testGetDocumentOnCondition(){
        mergeHandler.getDocumentOnCondition(dataBaseName, collectionName, condition);
    }

    @Test
    public void testUpdateDocument(){
        mergeHandler.updateDocument(dataBaseName, collectionName, input, updateInput);
    }

    @Test
    public void testDropCollection(){
        mergeHandler.dropDocument(dataBaseName, collectionName, condition);
    }

}
