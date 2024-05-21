package com.ibm.example.codeengine.services;

import java.util.Collection;

import com.ibm.example.codeengine.beans.FootballRequest;
import com.ibm.example.codeengine.beans.FootballResponse;
import com.ibm.example.codeengine.beans.QuoteRequest;
import com.ibm.example.codeengine.beans.QuoteResponse;
import com.ibm.example.codeengine.beans.TVGuideChannel;
import com.ibm.example.codeengine.beans.TVGuideRequest;
import com.ibm.example.codeengine.beans.TVGuideResponse;
import com.ibm.example.codeengine.beans.WeatherRequest;
import com.ibm.example.codeengine.beans.WeatherResponse;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class RESTClients {

    private static RESTClients instance = null;
    private final Client client = ClientBuilder.newClient();

    private RESTClients() {} 

    public static synchronized RESTClients getInstance() {
        if (instance == null) {
            instance = new RESTClients();
        }
        
        return instance;
    }
    
    public QuoteResponse callQuote(QuoteRequest request, String url) {        

        QuoteResponse quoteResponse = new QuoteResponse();

        Response response = client.target(url)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(request));

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            WebApplicationException e = response.readEntity(WebApplicationException.class);
            System.err.println(response.getStatus() + " : " + e.getMessage());
        }
  
        quoteResponse = response.readEntity(QuoteResponse.class);
        
        return quoteResponse;
    }

    public FootballResponse callFootball(FootballRequest request, String url) {

        FootballResponse footballResponse = new FootballResponse();

        Response response = client.target(url)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(request));

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            WebApplicationException e = response.readEntity(WebApplicationException.class);
            System.err.println(response.getStatus() + " : " + e.getMessage());
        }

        footballResponse = response.readEntity(FootballResponse.class);

        return footballResponse;
    }

    public WeatherResponse callWeather(WeatherRequest request, String url) {
        WeatherResponse weatherResponse = new WeatherResponse();

        Response response = client.target(url)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(request));

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            WebApplicationException e = response.readEntity(WebApplicationException.class);
            System.err.println(response.getStatus() + " : " + e.getMessage());
        }

        weatherResponse = response.readEntity(WeatherResponse.class);

        return weatherResponse;
    }

    public TVGuideResponse callTVGuide(TVGuideRequest request, String url) {
        TVGuideResponse tvGuideResponse = new TVGuideResponse();

        Response response = client.target(url)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.json(request));

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            WebApplicationException e = response.readEntity(WebApplicationException.class);
            System.err.println(response.getStatus() + " : " + e.getMessage());
        }

        Collection<TVGuideChannel> collection = response.readEntity(new GenericType<Collection<TVGuideChannel>>() {} );
        tvGuideResponse.setProgram(collection);
        return tvGuideResponse;
    }

}
