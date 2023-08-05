package ru.avalc.accountsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.avalc.accountsservice.model.Account;
import ru.avalc.accountsservice.model.Customer;
import ru.avalc.accountsservice.repo.AccountsRepository;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@RestController
public class AccountsController {

    private final AccountsRepository repository;

    @Autowired
    public AccountsController(AccountsRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/myAccount")
    public Account getAccountDetails(@RequestBody Customer customer) {

        return repository.findByCustomerId(customer.getCustomerId());
    }
}
