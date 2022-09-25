package com.unitech.unitech.dto.converter;

import com.unitech.unitech.dto.CurrencyExchangeDto;
import com.unitech.unitech.model.CurrencyExchange;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeDtoConverter {
    private final ModelMapper mapper;

    public CurrencyExchangeDtoConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CurrencyExchangeDto convert(CurrencyExchange currencyExchange){
        return mapper.map(currencyExchange,CurrencyExchangeDto.class);
    }
}
