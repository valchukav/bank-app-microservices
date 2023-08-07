package ru.avalc.cardsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@SpringBootApplication
public class CardsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardsServiceApplication.class, args);
    }

}
