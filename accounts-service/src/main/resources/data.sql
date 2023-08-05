INSERT INTO `customers` (`customer_id`, `name`,`email`,`mobile_number`,`create_date`)
VALUES (1, 'Avalc','avalc@gmail.com','9876548337',CURDATE());

INSERT INTO `accounts` (`customer_id`, `account_number`, `account_type`, `branch_address`, `create_date`)
VALUES (1, 186576453, 'Savings', '123 Main Street, New York', CURDATE());