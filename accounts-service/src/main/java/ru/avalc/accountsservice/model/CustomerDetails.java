package ru.avalc.accountsservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Alexei Valchuk, 08.08.2023, email: a.valchukav@gmail.com
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDetails {

    private Account account;
    private List<Object> cards;
    private List<Object> loans;
}
