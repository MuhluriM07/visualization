package sparkdigital.co.za.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import sparkdigital.co.za.deserializer.IntegerWithSpacesDeserializer;

@Entity
public class DataEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("Date")
    private String date;

    @JsonProperty("Total Confirmed Cases")
    @JsonDeserialize(using = IntegerWithSpacesDeserializer.class) // Use custom deserializer
    private int totalConfirmedCases;

    @JsonProperty("Total Deaths")
    @JsonDeserialize(using = IntegerWithSpacesDeserializer.class) // Use custom deserializer
    private int totalDeaths;

    @JsonProperty("Total Recovered")
    @JsonDeserialize(using = IntegerWithSpacesDeserializer.class) // Use custom deserializer
    private int totalRecovered;

    @JsonProperty("Active Cases")
    @JsonDeserialize(using = IntegerWithSpacesDeserializer.class) // Use custom deserializer
    private int activeCases;

    @JsonProperty("Daily Confirmed Cases")
    @JsonDeserialize(using = IntegerWithSpacesDeserializer.class) // Use custom deserializer
    private int dailyConfirmedCases;

    @JsonProperty("Daily  deaths")
    @JsonDeserialize(using = IntegerWithSpacesDeserializer.class) // Use custom deserializer
    private int dailyDeaths;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalConfirmedCases() {
        return totalConfirmedCases;
    }

    public void setTotalConfirmedCases(int totalConfirmedCases) {
        this.totalConfirmedCases = totalConfirmedCases;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public int getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(int activeCases) {
        this.activeCases = activeCases;
    }

    public int getDailyConfirmedCases() {
        return dailyConfirmedCases;
    }

    public void setDailyConfirmedCases(int dailyConfirmedCases) {
        this.dailyConfirmedCases = dailyConfirmedCases;
    }

    public int getDailyDeaths() {
        return dailyDeaths;
    }

    public void setDailyDeaths(int dailyDeaths) {
        this.dailyDeaths = dailyDeaths;
    }
 
}