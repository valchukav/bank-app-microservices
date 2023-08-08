package ru.avalc.accountsservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.avalc.accountsservice.model.Customer;
import ru.avalc.accountsservice.model.Loan;

import java.util.List;

/**
 * @author Alexei Valchuk, 08.08.2023, email: a.valchukav@gmail.com
 */

@FeignClient(name = "LOANS-SERVICE")
public interface LoansFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "myLoans", consumes = "application/json")
    List<Loan> getLoanDetails(@RequestBody Customer customer);
}
