package ru.avalc.accountsservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.avalc.accountsservice.config.AccountsServiceConfig;
import ru.avalc.accountsservice.model.Account;
import ru.avalc.accountsservice.model.Customer;
import ru.avalc.accountsservice.model.Properties;
import ru.avalc.accountsservice.repo.AccountsRepository;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@RestController
public class AccountsController {

    private final AccountsRepository repository;
    private final AccountsServiceConfig accountsServiceConfig;

    @Autowired
    public AccountsController(AccountsRepository repository, AccountsServiceConfig accountsServiceConfig) {
        this.repository = repository;
        this.accountsServiceConfig = accountsServiceConfig;
    }

    @PostMapping("/myAccount")
    public Account getAccountDetails(@RequestBody Customer customer) {

        return repository.findByCustomerId(customer.getCustomerId());
    }

    @GetMapping("/account/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountsServiceConfig.getMsg(), accountsServiceConfig.getBuildVersion(),
                accountsServiceConfig.getMailDetails());
        return ow.writeValueAsString(properties);
    }
}
