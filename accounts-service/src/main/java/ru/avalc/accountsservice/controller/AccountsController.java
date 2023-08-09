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
import ru.avalc.accountsservice.model.*;
import ru.avalc.accountsservice.repo.AccountsRepository;
import ru.avalc.accountsservice.service.client.CardsFeignClient;
import ru.avalc.accountsservice.service.client.LoansFeignClient;

import java.util.List;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@RestController
public class AccountsController {

    private final AccountsRepository repository;
    private final AccountsServiceConfig accountsServiceConfig;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    @Autowired
    public AccountsController(AccountsRepository repository, AccountsServiceConfig accountsServiceConfig, CardsFeignClient cardsFeignClient, LoansFeignClient loansFeignClient) {
        this.repository = repository;
        this.accountsServiceConfig = accountsServiceConfig;
        this.cardsFeignClient = cardsFeignClient;
        this.loansFeignClient = loansFeignClient;
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

    @PostMapping("/customerDetails")
    public CustomerDetails getCustomerDetails(@RequestBody Customer customer) {
        Account account = repository.findByCustomerId(customer.getCustomerId());
        List<Object> cardDetails = cardsFeignClient.getCardDetails(customer);
        List<Object> loanDetails = loansFeignClient.getLoanDetails(customer);

        return new CustomerDetails(account, cardDetails, loanDetails);
    }
}
