DELIMITER //

  CREATE TABLE savings_accounts (
  account_id INT PRIMARY KEY,
  customer_id INT,
  balance DECIMAL(10, 2)
);

CREATE TABLE employees (
  employee_id INT PRIMARY KEY,
  department_id INT,
  salary DECIMAL(10, 2)
);

CREATE TABLE accounts (
  account_id INT PRIMARY KEY,
  customer_id INT,
  balance DECIMAL(10, 2)
);

INSERT INTO savings_accounts (account_id, customer_id, balance) VALUES (101, 1, 1000.00);
INSERT INTO savings_accounts (account_id, customer_id, balance) VALUES (102, 2, 2000.00);
INSERT INTO savings_accounts (account_id, customer_id, balance) VALUES (103, 1, 1500.00);

INSERT INTO employees (employee_id, department_id, salary) VALUES (1, 10, 5000.00);
INSERT INTO employees (employee_id, department_id, salary) VALUES (2, 10, 6000.00);
INSERT INTO employees (employee_id, department_id, salary) VALUES (3, 20, 4000.00);

INSERT INTO accounts (account_id, customer_id, balance) VALUES (101, 1, 1000.00);
INSERT INTO accounts (account_id, customer_id, balance) VALUES (102, 2, 2000.00);
INSERT INTO accounts (account_id, customer_id, balance) VALUES (103, 1, 3000.00);


CREATE PROCEDURE ProcessMonthlyInterest()
BEGIN
  UPDATE savings_accounts
  SET balance = balance * 1.01;

  SELECT * FROM savings_accounts;
END //

CREATE PROCEDURE UpdateEmployeeBonus(
  IN dept_id INT,
  IN bonus_percentage DECIMAL(5, 2)
)
BEGIN
  UPDATE employees
  SET salary = salary * (1 + bonus_percentage / 100)
  WHERE department_id = dept_id;

  SELECT * FROM employees WHERE department_id = dept_id;
END //

CREATE PROCEDURE TransferFunds(
  IN src_account_id INT,
  IN tgt_account_id INT,
  IN amt DECIMAL(10, 2)
)
BEGIN
  DECLARE v_source_balance DECIMAL(10, 2);

  SELECT balance INTO v_source_balance
  FROM accounts
  WHERE account_id = src_account_id;

  IF v_source_balance < amt THEN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Insufficient funds';
  END IF;

  UPDATE accounts
  SET balance = balance - amt
  WHERE account_id = src_account_id;

  UPDATE accounts
  SET balance = balance + amt
  WHERE account_id = tgt_account_id;

  SELECT * FROM accounts WHERE account_id IN (src_account_id, tgt_account_id);
END //

DELIMITER ;

-- calling the procedures
CALL ProcessMonthlyInterest();
CALL UpdateEmployeeBonus(10, 10);
CALL TransferFunds(101, 102, 500.00);
