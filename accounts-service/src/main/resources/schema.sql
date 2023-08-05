DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS accounts;

CREATE TABLE `customers` (
    `customer_id` bigint AUTO_INCREMENT  PRIMARY KEY,
    `name` varchar(100) NOT NULL,
    `email` varchar(100) NOT NULL,
    `mobile_number` varchar(20) NOT NULL,
    `create_date` date DEFAULT NULL
);

CREATE TABLE `accounts` (
    `customer_id` bigint NOT NULL,
    `account_number` bigint AUTO_INCREMENT  PRIMARY KEY,
    `account_type` varchar(100) NOT NULL,
    `branch_address` varchar(200) NOT NULL,
    `create_date` date DEFAULT NULL
);