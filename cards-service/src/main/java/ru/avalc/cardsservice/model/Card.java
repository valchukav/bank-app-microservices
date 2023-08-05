package ru.avalc.cardsservice.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@Entity(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_id")
    private long cardId;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "total_limit")
    private BigDecimal totalLimit;

    @Column(name = "amount_used")
    private BigDecimal amountUsed;

    @Column(name = "available_amount")
    private BigDecimal availableAmount;

    @Column(name = "create_date")
    private LocalDate createDate;
}
