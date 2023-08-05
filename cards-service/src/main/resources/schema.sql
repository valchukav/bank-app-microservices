DROP TABLE IF EXISTS cards;

CREATE TABLE `cards` (
    `card_id` bigint NOT NULL AUTO_INCREMENT,
    `card_number` varchar(100) NOT NULL,
    `customer_id` bigint NOT NULL,
    `card_type` varchar(100) NOT NULL,
    `total_limit` numeric NOT NULL,
    `amount_used` numeric NOT NULL,
    `available_amount` numeric NOT NULL,
    `create_date` date DEFAULT NULL,
    PRIMARY KEY (`card_id`)
);