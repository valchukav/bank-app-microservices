DROP TABLE IF EXISTS loans;

CREATE TABLE `loans` (
    `loan_number` bigint NOT NULL AUTO_INCREMENT,
    `customer_id` bigint NOT NULL,
    `start_date` date NOT NULL,
    `loan_type` varchar(100) NOT NULL,
    `total_loan` numeric NOT NULL,
    `amount_paid` numeric NOT NULL,
    `outstanding_amount` numeric NOT NULL,
    `create_date` date DEFAULT NULL,
    PRIMARY KEY (`loan_number`));