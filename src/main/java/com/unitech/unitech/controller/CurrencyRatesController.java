package com.unitech.unitech.controller;

import com.unitech.unitech.dto.CurrencyExchangeDto;
import com.unitech.unitech.dto.request.Currency;
import com.unitech.unitech.service.CurrencyRatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rates")
public class CurrencyRatesController {

    private final CurrencyRatesService currencyRatesService;

    public CurrencyRatesController(CurrencyRatesService currencyRatesService) {
        this.currencyRatesService = currencyRatesService;
    }

    @GetMapping("/test")
    @Operation(summary = "Test", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CurrencyExchangeDto> currencyRates(
                                                          @RequestParam Currency symbol,
                                                          @RequestParam Currency base){
        return ResponseEntity.ok(currencyRatesService.getExchange(symbol,base));
    }

}
