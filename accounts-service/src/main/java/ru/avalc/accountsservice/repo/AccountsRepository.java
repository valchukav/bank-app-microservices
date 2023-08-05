package ru.avalc.accountsservice.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.avalc.accountsservice.model.Account;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {

    Account findByCustomerId(int customerId);
}
