package ru.avalc.loansservice.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.avalc.loansservice.model.Loan;

import java.util.List;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@Repository
public interface LoansRepository extends CrudRepository<Loan, Long> {

    List<Loan> findByCustomerIdOrderByStartDateDesc(int customerId);
}
