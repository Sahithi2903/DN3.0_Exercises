CREATE TABLE accounts (
  account_id INT PRIMARY KEY,
  customer_id INT,
  balance DECIMAL(10, 2)
);

CREATE TABLE employees (
  employee_id INT PRIMARY KEY,
  salary DECIMAL(10, 2)
);

CREATE TABLE customers (
  customer_id INT PRIMARY KEY,
  name VARCHAR(100)
);

INSERT INTO accounts VALUES (101, 1, 1000.00);
INSERT INTO accounts VALUES (102, 2, 2000.00);

INSERT INTO employees VALUES (1, 5000.00);
INSERT INTO employees VALUES (2, 6000.00);

INSERT INTO customers VALUES (1, 'John Doe');
INSERT INTO customers (customer_id, name) VALUES (2, 'Jane Smith');

DELIMITER //

CREATE PROCEDURE SafeTransferFunds (
  IN source_account_id INT,
  IN target_account_id INT,
  IN amount DECIMAL(10, 2)
)
BEGIN
  DECLARE v_source_balance DECIMAL(10, 2);
  DECLARE v_target_balance DECIMAL(10, 2);

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SELECT 'Error during transfer' AS message;
  END;

  START TRANSACTION;

  SELECT balance INTO v_source_balance FROM accounts WHERE account_id = source_account_id;
  SELECT balance INTO v_target_balance FROM accounts WHERE account_id = target_account_id;

  IF v_source_balance < amount THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Insufficient funds';
  ELSE
    UPDATE accounts SET balance = balance - amount WHERE account_id = source_account_id;
    UPDATE accounts SET balance = balance + amount WHERE account_id = target_account_id;
    COMMIT;
    SELECT 'Transfer completed' AS message;
  END IF;
END //

CREATE PROCEDURE UpdateSalary (
  IN emp_id INT,
  IN percentage DECIMAL(5, 2)
)
BEGIN
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SELECT 'Error updating salary' AS message;
  END;

  START TRANSACTION;

  UPDATE employees SET salary = salary * (1 + percentage / 100) WHERE employee_id = emp_id;

  IF ROW_COUNT() = 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Employee not found';
  ELSE
    COMMIT;
    SELECT 'Salary updated' AS message;
  END IF;
END //

CREATE PROCEDURE AddNewCustomer (
  IN cust_id INT,
  IN cust_name VARCHAR(100)
)
BEGIN
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SELECT 'Error adding customer' AS message;
  END;

  START TRANSACTION;

  INSERT INTO customers (customer_id, name) VALUES (cust_id, cust_name);

  COMMIT;
  SELECT 'Customer added' AS message;
END //

DELIMITER ;

-- Testing the procedures
CALL SafeTransferFunds(101, 102, 500.00);
CALL UpdateSalary(1, 10.00);
CALL AddNewCustomer(3, 'Alice Brown');

