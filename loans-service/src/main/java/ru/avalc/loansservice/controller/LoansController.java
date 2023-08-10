package ru.avalc.loansservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.avalc.loansservice.config.LoansServiceConfig;
import ru.avalc.loansservice.model.Customer;
import ru.avalc.loansservice.model.Loan;
import ru.avalc.loansservice.model.Properties;
import ru.avalc.loansservice.repo.LoansRepository;

import java.util.List;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@RestController
public class LoansController {

    private final LoansRepository repository;
    private final LoansServiceConfig loansServiceConfig;

    @Autowired
    public LoansController(LoansRepository repository, LoansServiceConfig loansServiceConfig) {
        this.repository = repository;
        this.loansServiceConfig = loansServiceConfig;
    }

    @PostMapping("/myLoans")
    public List<Loan> getCustomerLoans(@RequestHeader("bank-app-correlation-id") String correlationId, @RequestBody Customer customer) {

        return repository.findByCustomerIdOrderByStartDateDesc(customer.getCustomerId());
    }

    @GetMapping("/loans/properties")
    public String getPropertyDetails(@RequestHeader("bank-app-correlation-id") String correlationId) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(loansServiceConfig.getMsg(), loansServiceConfig.getBuildVersion(),
                loansServiceConfig.getMailDetails());
        return ow.writeValueAsString(properties);
    }
}
