package ru.avalc.accountsservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.avalc.accountsservice.config.AccountsServiceConfig;
import ru.avalc.accountsservice.model.Account;
import ru.avalc.accountsservice.model.Customer;
import ru.avalc.accountsservice.model.CustomerDetails;
import ru.avalc.accountsservice.model.Properties;
import ru.avalc.accountsservice.repo.AccountsRepository;
import ru.avalc.accountsservice.service.client.CardsFeignClient;
import ru.avalc.accountsservice.service.client.LoansFeignClient;

import java.util.List;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@RateLimiter(name = "accountsControllerRateLimiter")
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
    public Account getAccountDetails(@RequestHeader("bank-app-correlation-id") String correlationId, @RequestBody Customer customer) {

        return repository.findByCustomerId(customer.getCustomerId());
    }

    @GetMapping("/account/properties")
    public String getPropertyDetails(@RequestHeader("bank-app-correlation-id") String correlationId) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountsServiceConfig.getMsg(), accountsServiceConfig.getBuildVersion(),
                accountsServiceConfig.getMailDetails());
        return ow.writeValueAsString(properties);
    }

    @Timed(value = "getCustomerDetails.time", description = "Time taken to return Customer details")
    @PostMapping("/customerDetails")
    @CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "customerDetailsFallback")
    public CustomerDetails getCustomerDetails(@RequestHeader("bank-app-correlation-id") String correlationId, @RequestBody Customer customer) {
        CustomerDetails customerDetails = new CustomerDetails();

        Account account = repository.findByCustomerId(customer.getCustomerId());

        customerDetails.setAccount(account);

        getCardDetails(correlationId, customer, customerDetails);
        getLoanDetails(correlationId, customer, customerDetails);

        return customerDetails;
    }

    private CustomerDetails customerDetailsFallback(@RequestHeader("bank-app-correlation-id") String correlationId, Customer customer, Throwable throwable) {
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccount(repository.findByCustomerId(customer.getCustomerId()));

        String excMessage = throwable.getMessage().toLowerCase();

        if (!excMessage.contains("cards")) {
            getCardDetails(correlationId, customer, customerDetails);
        } else {
            setEmptyCards(customerDetails);
        }

        if (!excMessage.contains("loans")) {
            getLoanDetails(correlationId, customer, customerDetails);
        } else {
            setEmptyLoans(customerDetails);
        }

        return customerDetails;
    }

    private void getCardDetails(String correlationId, Customer customer, CustomerDetails customerDetails) {
        try {
            List<Object> cardDetails = cardsFeignClient.getCardDetails(correlationId, customer);
            customerDetails.setCards(cardDetails);
        } catch (Exception exception) {
            setEmptyCards(customerDetails);
        }
    }

    private void getLoanDetails(String correlationId, Customer customer, CustomerDetails customerDetails) {
        try {
            List<Object> loanDetails = loansFeignClient.getLoanDetails(correlationId, customer);
            customerDetails.setLoans(loanDetails);
        } catch (Exception exception) {
            setEmptyLoans(customerDetails);
        }
    }

    private void setEmptyCards(CustomerDetails customerDetails) {
        customerDetails.setCards(List.of("Cards service is not available now. Try it later."));
    }

    private void setEmptyLoans(CustomerDetails customerDetails) {
        customerDetails.setLoans(List.of("Loans service is not available now. Try it later."));
    }
}
