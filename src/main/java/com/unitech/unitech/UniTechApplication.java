package com.unitech.unitech;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "UniTech App",
                description = "Everything is possible",
                version = "v1"
        )
)
public class UniTechApplication implements CommandLineRunner {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public UniTechApplication(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(UniTechApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 100; i++)
        kafkaTemplate.send("user-activation-events","key" + i,"Hello, I am Parvin");
    }
}
