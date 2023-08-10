package ru.avalc.accountsservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import feign.FeignException;
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
        Account account = repository.findByCustomerId(customer.getCustomerId());
        List<Object> cardDetails = cardsFeignClient.getCardDetails(correlationId, customer);
        List<Object> loanDetails = loansFeignClient.getLoanDetails(correlationId, customer);

        return new CustomerDetails(account, cardDetails, loanDetails);
    }

    private CustomerDetails customerDetailsFallback(@RequestHeader("bank-app-correlation-id") String correlationId, Customer customer, Throwable throwable) {
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccount(repository.findByCustomerId(customer.getCustomerId()));

        try {
            List<Object> cardDetails = cardsFeignClient.getCardDetails(correlationId, customer);
            customerDetails.setCards(cardDetails);
        } catch (FeignException exception) {
            customerDetails.setCards(List.of("Cards service is not available now. Try it later."));
        }

        try {
            List<Object> loanDetails = loansFeignClient.getLoanDetails(correlationId, customer);
            customerDetails.setLoans(loanDetails);
        } catch (FeignException exception) {
            customerDetails.setLoans(List.of("Loans service is not available now. Try it later."));
        }

        return customerDetails;
    }
}
