package com.ibm.example.codeengine.beans;

import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WeatherResponse implements Serializable {
    
    private String conditions;
    private String temperature;
    private String humidity;
    private String percipitation;
    private String windSpeed;
    
    public String getConditions() {
        return conditions;
    }
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    public String gethumidity() {
        return humidity;
    }
    public void sethumidity(String humidity) {
        this.humidity = humidity;
    }
    public String getPercipitation() {
        return percipitation;
    }
    public void setPercipitation(String percipitation) {
        this.percipitation = percipitation;
    }
    public String getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }


}
