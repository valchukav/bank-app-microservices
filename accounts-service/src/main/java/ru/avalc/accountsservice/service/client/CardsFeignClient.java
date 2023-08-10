package ru.avalc.accountsservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.avalc.accountsservice.model.Customer;

import java.util.List;

/**
 * @author Alexei Valchuk, 08.08.2023, email: a.valchukav@gmail.com
 */

@FeignClient(name = "CARDS-SERVICE")
public interface CardsFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "myCards", consumes = "application/json")
    List<Object> getCardDetails(@RequestHeader("bank-app-correlation-id") String correlationId, @RequestBody Customer customer);
}
