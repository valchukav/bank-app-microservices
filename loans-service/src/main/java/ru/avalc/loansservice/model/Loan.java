package ru.avalc.loansservice.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Alexei Valchuk, 04.08.2023, email: a.valchukav@gmail.com
 */

@Entity(name = "loans")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_number")
    private long loanNumber;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "loan_type")
    private String loanType;

    @Column(name = "total_loan")
    private BigDecimal totalLoan;

    @Column(name = "amount_paid")
    private BigDecimal amountPaid;

    @Column(name = "outstanding_amount")
    private BigDecimal outstandingAmount;

    @Column(name = "create_date")
    private LocalDate createDate;
}
