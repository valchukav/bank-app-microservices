package ru.avalc.cardsservice.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.avalc.cardsservice.model.Card;

import java.util.List;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@Repository
public interface CardsRepository extends CrudRepository<Card, Long> {

    List<Card> findByCustomerId(long customerId);
}
