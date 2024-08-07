
CREATE TABLE Customers (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    balance DECIMAL(10, 2),
    IsVIP BOOLEAN DEFAULT FALSE
);

CREATE TABLE Transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    transaction_type VARCHAR(50),
    amount DECIMAL(10, 2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

CREATE TABLE Accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    account_type VARCHAR(50),
    balance DECIMAL(10, 2),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

CREATE TABLE Loans (
    loan_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    loan_amount DECIMAL(10, 2),
    interest_rate DECIMAL(5, 2),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

INSERT INTO Customers (customer_id, name, age, balance) VALUES 
(1, 'John Doe', 65, 15000.00),
(2, 'Jane Smith', 58, 8000.00),
(3, 'George Brown', 62, 12000.00),
(4, 'Emily White', 45, 2000.00);

INSERT INTO Transactions (customer_id, transaction_type, amount, transaction_date) VALUES 
(1, 'Deposit', 500.00, '2024-08-01'),
(2, 'Withdrawal', 2000.00, '2024-08-05'),
(1, 'Deposit', 300.00, '2024-08-15'),
(3, 'Withdrawal', 1500.00, '2024-08-20');

INSERT INTO Accounts (customer_id, account_type, balance) VALUES 
(1, 'Savings', 5000.00),
(2, 'Savings', 3000.00),
(3, 'Checking', 1500.00),
(1, 'Checking', 2000.00);

INSERT INTO Loans (customer_id, loan_amount, interest_rate) VALUES 
(1, 10000.00, 5.00),
(2, 5000.00, 4.50),
(3, 15000.00, 6.00);

DELIMITER //

CREATE PROCEDURE GenerateMonthlyStatements()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_customer_id INT;
    DECLARE v_customer_name VARCHAR(100);
    DECLARE v_amount DECIMAL(10, 2);
    DECLARE v_transaction_type VARCHAR(50);
    DECLARE cur CURSOR FOR 
        SELECT DISTINCT c.customer_id, c.name
        FROM Customers c
        JOIN Transactions t ON c.customer_id = t.customer_id
        WHERE MONTH(t.transaction_date) = MONTH(CURRENT_DATE())
        AND YEAR(t.transaction_date) = YEAR(CURRENT_DATE());
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO v_customer_id, v_customer_name;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        SELECT CONCAT('Statement for ', v_customer_name) AS Statement;
        SELECT t.transaction_date, t.transaction_type, t.amount
        FROM Transactions t
        WHERE t.customer_id = v_customer_id
        AND MONTH(t.transaction_date) = MONTH(CURRENT_DATE())
        AND YEAR(t.transaction_date) = YEAR(CURRENT_DATE());
        
    END LOOP;

    CLOSE cur;
END //

DELIMITER //

CREATE PROCEDURE ApplyAnnualFee()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_account_id INT;
    DECLARE v_balance DECIMAL(10, 2);
    DECLARE cur CURSOR FOR 
        SELECT account_id, balance
        FROM Accounts;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO v_account_id, v_balance;
        IF done THEN
            LEAVE read_loop;
        END IF;

        UPDATE Accounts
        SET balance = balance - 50.00 -- Annual fee amount
        WHERE account_id = v_account_id;
    END LOOP;

    CLOSE cur;
END //

DELIMITER //

CREATE PROCEDURE UpdateLoanInterestRates(
    IN new_interest_rate DECIMAL(5, 2)
)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_loan_id INT;
    DECLARE v_current_rate DECIMAL(5, 2);
    DECLARE cur CURSOR FOR 
        SELECT loan_id, interest_rate
        FROM Loans;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO v_loan_id, v_current_rate;
        IF done THEN
            LEAVE read_loop;
        END IF;

        UPDATE Loans
        SET interest_rate = new_interest_rate
        WHERE loan_id = v_loan_id;
    END LOOP;

    CLOSE cur;
END //

DELIMITER ;

CALL GenerateMonthlyStatements();

CALL ApplyAnnualFee();

CALL UpdateLoanInterestRates(4.00);

SELECT * FROM Accounts;
SELECT * FROM Loans;
SELECT * FROM Transactions;
