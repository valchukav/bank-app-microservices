package ru.avalc.loansservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.avalc.loansservice.model.Customer;
import ru.avalc.loansservice.model.Loan;
import ru.avalc.loansservice.repo.LoansRepository;

import java.util.List;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@RestController
public class LoansController {

    private final LoansRepository repository;

    @Autowired
    public LoansController(LoansRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/myLoans")
    public List<Loan> getCustomerLoans(@RequestBody Customer customer) {

        return repository.findByCustomerIdOrderByStartDateDesc(customer.getCustomerId());
    }
}
