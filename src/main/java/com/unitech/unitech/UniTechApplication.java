package com.unitech.unitech;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "UniTech App",
                description = "Everything is possible",
                version = "v1"
        )
)
public class UniTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniTechApplication.class, args);
    }

}
