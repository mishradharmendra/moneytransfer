package com.moneytransfer;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.AppModule;
import com.Startup;
import com.entrypoint.EntrypointType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class AccountTransferApiTest {

	public static final String BASE_URL = "http://localhost:8000/api/moneybank/accounts";
    ObjectMapper mapper = new ObjectMapper();
    
    
    @BeforeClass
	public synchronized static void setup() {
		String [] args = {"8000"};
		var injector = Guice.createInjector(new AppModule());
        injector.getInstance(Startup.class).boot(EntrypointType.REST, args);
        System.out.println("SERVER IS STARTED");
	}

	@AfterClass
	public synchronized static void completed() {
	}
	
    @Test
    public void shouldAbleToTransferAmount() throws UnirestException, JsonParseException, JsonMappingException, IOException{
        String testJson = "{\n" +
                "\"name\" : \"dharmendra\",\n" +
                "\"amount\" : 1200\n" +
                "}";
        HttpResponse<JsonNode> jsonResponse = Unirest.post( BASE_URL)
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HttpStatus.SC_CREATED);
        
        String testJson1 = "{\n" +
                "\"name\" : \"dharmendra\",\n" +
                "\"amount\" : 200\n" +
                "}";
        HttpResponse<JsonNode> jsonResponse1 = Unirest.post( BASE_URL)
                .header("accept", "application/json")
                .body(testJson1)
                .asJson();
        Assert.assertEquals(jsonResponse1.getStatus(), HttpStatus.SC_CREATED);
        
        String transferJson = "{\n" +
                "\"accountFrom\" : 1,\n" +
                "\"accountTo\" : 2,\n" +
                "\"amount\" : 500\n" +
                "}";
        HttpResponse<JsonNode> transferRes = Unirest.post( BASE_URL + "/transfer")
                .header("accept", "application/json")
                .body(transferJson)
                .asJson();
        Assert.assertEquals(transferRes.getStatus(), HttpStatus.SC_OK);
        
        HttpResponse<JsonNode> accountResponse = Unirest.get(BASE_URL + "/balance/1" ).asJson();
        var accountDto =  mapper.readValue(accountResponse.getRawBody(), AccountTest[].class);
        Assert.assertTrue(new BigDecimal("700").compareTo(accountDto[0].getBalance()) == 0);
        
        HttpResponse<JsonNode> accountResponse1 = Unirest.get(BASE_URL + "/balance/2" ).asJson();
        var accountDto1 =  mapper.readValue(accountResponse1.getRawBody(), AccountTest[].class);
        Assert.assertTrue(new BigDecimal("700").compareTo(accountDto1[0].getBalance()) == 0);

    }


    @Test
    public void shouldNotAbleToTransferAmount() throws UnirestException, JsonParseException, JsonMappingException, IOException{
        String testJson = "{\n" +
                "\"name\" : \"dharmendra\",\n" +
                "\"amount\" : 1200\n" +
                "}";
        HttpResponse<JsonNode> jsonResponse = Unirest.post( BASE_URL)
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HttpStatus.SC_CREATED);
        
        String testJson1 = "{\n" +
                "\"name\" : \"dharmendra\",\n" +
                "\"amount\" : 200\n" +
                "}";
        HttpResponse<JsonNode> jsonResponse1 = Unirest.post( BASE_URL)
                .header("accept", "application/json")
                .body(testJson1)
                .asJson();
        Assert.assertEquals(jsonResponse1.getStatus(), HttpStatus.SC_CREATED);
        
        String transferJson = "{\n" +
                "\"accountFrom\" : 2,\n" +
                "\"accountTo\" : 1,\n" +
                "\"amount\" : 1500\n" +
                "}";
        HttpResponse<JsonNode> transferRes = Unirest.post( BASE_URL + "/transfer")
                .header("accept", "application/json")
                .body(transferJson)
                .asJson();
        Assert.assertEquals(transferRes.getStatus(), 500);
        
    }


}
