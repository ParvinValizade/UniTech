package com.unitech.unitech.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Map;

public class CurrencyExchange {
    private Boolean success;
    private String timestamp;
    private String base;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String date;
    private Map<String,Double> rates;

    public CurrencyExchange() {
    }

    public CurrencyExchange(Boolean success, String timestamp,
                            String base, String date, Map<String, Double> rates) {
        this.success = success;
        this.timestamp = timestamp;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "CurrencyExchange{" +
                "success=" + success +
                ", timestamp='" + timestamp + '\'' +
                ", base='" + base + '\'' +
                ", date=" + date +
                ", rates=" + rates +
                '}';
    }
}
