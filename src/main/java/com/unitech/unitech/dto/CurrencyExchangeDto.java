package com.unitech.unitech.dto;

import java.util.Map;

public class CurrencyExchangeDto {
    private String base;
    private String date;
    private Map<String,Double> rates;

    public CurrencyExchangeDto() {
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
}
