package com.unitech.unitech.beans;

import com.unitech.unitech.exception.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RequiredBeans {

    private final RestTemplateResponseErrorHandler responseErrorHandler;

    public RequiredBeans(RestTemplateResponseErrorHandler responseErrorHandler) {
        this.responseErrorHandler = responseErrorHandler;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder().errorHandler(responseErrorHandler).build();
    }
}
