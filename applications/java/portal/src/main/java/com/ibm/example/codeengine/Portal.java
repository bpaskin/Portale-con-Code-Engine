package com.ibm.example.codeengine;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.WebApplicationException;

import java.io.IOException;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.ibm.example.codeengine.beans.FootballRequest;
import com.ibm.example.codeengine.beans.FootballResponse;
import com.ibm.example.codeengine.beans.QuoteRequest;
import com.ibm.example.codeengine.beans.QuoteResponse;
import com.ibm.example.codeengine.beans.TVGuideRequest;
import com.ibm.example.codeengine.beans.TVGuideResponse;
import com.ibm.example.codeengine.beans.WeatherRequest;
import com.ibm.example.codeengine.beans.WeatherResponse;
import com.ibm.example.codeengine.services.RESTClients;

@WebServlet(urlPatterns = "/")
public class Portal extends HttpServlet {

    @Inject @ConfigProperty(name="category", defaultValue="sports")
    String quoteCategory;

    @Inject
    QuoteRequest quoteRequest;

    @Inject @ConfigProperty(name="apiKey", defaultValue="1")
    String apiKey;

    @Inject @ConfigProperty(name="leagueId", defaultValue="1")
    String leagueId;

    @Inject
    FootballRequest footballRequest;

    @Inject @ConfigProperty(name="latitude", defaultValue="1")
    String latitude;

    @Inject @ConfigProperty(name="longitude", defaultValue="1")
    String longitude;

    @Inject
    WeatherRequest weatherRequest;

    @Inject
    TVGuideRequest tvGuideRequest;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        StringBuffer url = request.getRequestURL();
        url = new StringBuffer("https://portaljava.1g3gcv5qwvkl.us-south.codeengine.appdomain.cloud");
        String footballUrl = url.toString().replace("portaljava", "football");
        String quotelUrl = url.toString().replace("portaljava", "quote");
        String tvguidelUrl = url.toString().replace("portaljava", "tvguide");
        String weatherlUrl = url.toString().replace("portaljava", "weather");

        quoteRequest.setCategory(quoteCategory);
        QuoteResponse quoteResponse = RESTClients.getInstance().callQuote(quoteRequest, quotelUrl);
        session.setAttribute("quote", quoteResponse);

        footballRequest.setApiKey(apiKey);
        footballRequest.setLeagueId(leagueId);
        FootballResponse footballResponse = RESTClients.getInstance().callFootball(footballRequest, footballUrl);
        session.setAttribute("football", footballResponse);

        weatherRequest.setLatitude(latitude);
        weatherRequest.setLongitude(longitude);
        WeatherResponse weatherResponse = RESTClients.getInstance().callWeather(weatherRequest, weatherlUrl);
        session.setAttribute("weather", weatherResponse);

        TVGuideResponse tvGuideResponse = RESTClients.getInstance().callTVGuide(tvGuideRequest, tvguidelUrl);
        session.setAttribute("tvGuide", tvGuideResponse);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}
