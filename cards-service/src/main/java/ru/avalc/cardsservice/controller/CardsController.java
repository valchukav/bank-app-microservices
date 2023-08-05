package ru.avalc.cardsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.avalc.cardsservice.model.Card;
import ru.avalc.cardsservice.model.Customer;
import ru.avalc.cardsservice.repo.CardsRepository;


import java.util.List;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@RestController
public class CardsController {

    private final CardsRepository repository;

    @Autowired
    public CardsController(CardsRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/myCards")
    public List<Card> getCustomerLoans(@RequestBody Customer customer) {

        return repository.findByCustomerId(customer.getCustomerId());
    }
}
