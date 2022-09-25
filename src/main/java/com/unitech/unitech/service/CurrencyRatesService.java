package com.unitech.unitech.service;

import com.unitech.unitech.dto.CurrencyExchangeDto;
import com.unitech.unitech.dto.converter.CurrencyExchangeDtoConverter;
import com.unitech.unitech.dto.request.Currency;
import com.unitech.unitech.model.CurrencyExchange;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyRatesService {
    private final RestTemplate restTemplate;
    private final CurrencyExchangeDtoConverter converter;

    @Value("${restTemplate.header.apikey}")
    private String apikey;

    public CurrencyRatesService(RestTemplate restTemplate, CurrencyExchangeDtoConverter converter) {
        this.restTemplate = restTemplate;
        this.converter = converter;
    }

   public CurrencyExchangeDto getExchange(Currency symbol, Currency base){

       ModelMapper mapper = new ModelMapper();
       final HttpHeaders headers = new HttpHeaders();
       headers.set("apikey",apikey);

       final HttpEntity<String> entity = new HttpEntity<>(headers);

       ResponseEntity<Map> response =
               restTemplate.exchange("https://api.apilayer.com/exchangerates_data/latest?symbols="+symbol.name()+
                               "&base="+base.name(),
                       HttpMethod.GET, entity, Map.class);

       CurrencyExchange currencyExchange = mapper.map(response.getBody(),CurrencyExchange.class);

       return converter.convert(currencyExchange);
   }

}
