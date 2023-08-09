package ru.avalc.cardsservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.avalc.cardsservice.config.CardsServiceConfig;
import ru.avalc.cardsservice.model.Card;
import ru.avalc.cardsservice.model.Customer;
import ru.avalc.cardsservice.model.Properties;
import ru.avalc.cardsservice.repo.CardsRepository;


import java.util.List;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@RestController
public class CardsController {

    private final CardsRepository repository;
    private final CardsServiceConfig cardsServiceConfig;

    @Autowired
    public CardsController(CardsRepository repository, CardsServiceConfig cardsServiceConfig) {
        this.repository = repository;
        this.cardsServiceConfig = cardsServiceConfig;
    }

    @PostMapping("/myCards")
    public List<Card> getCustomerCards(@RequestBody Customer customer) {

        return repository.findByCustomerId(customer.getCustomerId());
    }

    @GetMapping("/cards/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(cardsServiceConfig.getMsg(), cardsServiceConfig.getBuildVersion(),
                cardsServiceConfig.getMailDetails());
        return ow.writeValueAsString(properties);
    }
}
