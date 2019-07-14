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

public class AccountCrudApiTest  {
	
	@BeforeClass
	public synchronized static void setup() {
		String [] args = {"7000"};
		var injector = Guice.createInjector(new AppModule());
        injector.getInstance(Startup.class).boot(EntrypointType.REST, args);
        System.out.println("SERVER IS STARTED");
	}

	@AfterClass
	public synchronized static void completed() {
	}
	
	public static final String BASE_URL = "http://localhost:7000/api/moneybank/accounts";
    ObjectMapper mapper = new ObjectMapper();
    
	@Test
    public void testAccountIsCreatedSuccessfully() throws UnirestException{
        String testJson = "{\n" +
                "\"name\" : \"dharmendra\",\n" +
                "\"amount\" : 200\n" +
                "}";
        HttpResponse<JsonNode> jsonResponse = Unirest.post( BASE_URL)
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HttpStatus.SC_CREATED);
    }

    @Test
    public void testAccountIsCreatedWithValidBalance() throws UnirestException, JsonParseException, JsonMappingException, IOException{
    	String testJson = "{\n" +
                "\"name\" : \"dharmendra\",\n" +
                "\"amount\" : 200\n" +
                "}";
    	
        HttpResponse<JsonNode> jsonResponse = Unirest.post( BASE_URL)
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HttpStatus.SC_CREATED);
        
        HttpResponse<JsonNode> accountResponse = Unirest.get(BASE_URL + "/balance/1" ).asJson();
        var accountDto =  mapper.readValue(accountResponse.getRawBody(), AccountTest[].class);
        Assert.assertTrue(new BigDecimal("200").compareTo(accountDto[0].getBalance()) == 0);
        
    }


    @Test
    public void shouldNotallowCreatingAccountWithNegativeAmount() throws UnirestException{
    	String testJson = "{\n" +
                "\"name\" : \"dharmendra\",\n" +
                "\"amount\" : -200\n" +
                "}";
    	
        HttpResponse<JsonNode> jsonResponse = Unirest.post( BASE_URL)
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), 422);
    }
    
    @Test
    public void shouldAddBalanceCorrectly() throws UnirestException, JsonParseException, JsonMappingException, IOException{
    	String testJson = "{\n" +
                "\"name\" : \"dharmendra\",\n" +
                "\"amount\" : 200\n" +
                "}";
    	
        HttpResponse<JsonNode> jsonResponse = Unirest.post( BASE_URL)
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HttpStatus.SC_CREATED);
        
        testJson = "{\n" +
        		"\"id\" : 1,\n" +
                "\"name\" : \"dharmendra\",\n" +
                "\"amount\" : 1000\n" +
                "}";
        Unirest.post( BASE_URL + "/addBalance")
        .header("accept", "application/json")
        .body(testJson)
        .asJson();
        
        HttpResponse<JsonNode> accountResponse = Unirest.get(BASE_URL + "/balance/1" ).asJson();
        var accountDto =  mapper.readValue(accountResponse.getRawBody(), AccountTest[].class);
        Assert.assertTrue(new BigDecimal("1200").compareTo(accountDto[0].getBalance()) == 0);
        
    }
    
    @Test
    public void shouldNotWithdrawBalanceCorrectly() throws UnirestException, JsonParseException, JsonMappingException, IOException{
    	String testJson = "{\n" +
                "\"name\" : \"dharmendra\",\n" +
                "\"amount\" : 1200\n" +
                "}";
    	
        HttpResponse<JsonNode> jsonResponse = Unirest.post( BASE_URL)
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HttpStatus.SC_CREATED);
        
        testJson = "{\n" +
        		"\"id\" : 1,\n" +
                "\"name\" : \"dharmendra\",\n" +
                "\"amount\" : 12200\n" +
                "}";
        HttpResponse<JsonNode> res = Unirest.post( BASE_URL + "/withdraw")
        .header("accept", "application/json")
        .body(testJson)
        .asJson();
        Assert.assertEquals(res.getStatus(), 500);

    }
}
