INSERT INTO `loans` (`loan_number`, `customer_id`, `start_date`, `loan_type`, `total_loan`, `amount_paid`,
                     `outstanding_amount` , `create_date`)
VALUES (1, 1, CURDATE() - 250, 'Home', 200000, 50000, 150000, CURDATE() - 250);

INSERT INTO `loans` (`loan_number`, `customer_id`, `start_date`, `loan_type`, `total_loan`, `amount_paid`,
                     `outstanding_amount`, `create_date`)
VALUES (2, 1, CURDATE() - 376, 'Vehicle', 40000, 10000, 30000, CURDATE() - 376);

INSERT INTO `loans` (`loan_number`, `customer_id`, `start_date`, `loan_type`, `total_loan`, `amount_paid`,
                     `outstanding_amount` , `create_date`)
VALUES (3, 1, CURDATE() - 549, 'Home', 50000, 10000, 40000, CURDATE() - 549);

INSERT INTO `loans` (`loan_number`, `customer_id`, `start_date`, `loan_type`, `total_loan`, `amount_paid`,
                       `outstanding_amount` , `create_date`)
VALUES (4, 1, CURDATE() - 122, 'Personal', 10000, 3500, 6500, CURDATE() - 122);