CREATE TABLE customers (
  customer_id INT PRIMARY KEY,
  name VARCHAR(100),
  age INT,
  balance DECIMAL(10, 2),
  is_vip VARCHAR(3) DEFAULT 'N'
);

CREATE TABLE loans (
  loan_id INT PRIMARY KEY,
  customer_id INT,
  interest_rate DECIMAL(5, 2),
  due_date DATE,
  FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

INSERT INTO customers VALUES (1, 'John Doe', 65, 15000, 'N');
INSERT INTO customers VALUES (2, 'Jane Smith', 35, 5000, 'N');
INSERT INTO customers VALUES (3, 'Bob Johnson', 72, 25000, 'N');
INSERT INTO customers VALUES (4, 'Alice Brown', 40, 8000, 'N');

INSERT INTO loans VALUES (101, 1, 10, '2024-09-05');
INSERT INTO loans VALUES (102, 2, 12, '2024-08-15');
INSERT INTO loans VALUES (103, 3, 9, '2024-09-20');
INSERT INTO loans VALUES (104, 4, 11, '2024-08-25');

DELIMITER //
CREATE PROCEDURE update_customer_data()
BEGIN
  DECLARE v_customer_id INT;
  DECLARE v_age INT;
  DECLARE v_balance DECIMAL(10, 2);
  DECLARE v_loan_id INT;
  DECLARE v_interest_rate DECIMAL(5, 2);
  DECLARE v_due_date DATE;
  DECLARE no_more_rows BOOLEAN;
  DECLARE cursor_c1 CURSOR FOR SELECT customer_id, age, balance FROM customers;
  DECLARE cursor_c2 CURSOR FOR SELECT loan_id, customer_id, interest_rate, due_date FROM loans;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_rows = TRUE;
  
  -- Scenario 1:
  OPEN cursor_c1;
  SET no_more_rows = FALSE;
  REPEAT
    FETCH cursor_c1 INTO v_customer_id, v_age, v_balance;
    IF NOT no_more_rows THEN
      IF v_age > 60 THEN
        UPDATE loans SET interest_rate = interest_rate * 0.99 WHERE customer_id = v_customer_id;
      END IF;
    END IF;
  UNTIL no_more_rows END REPEAT;
  CLOSE cursor_c1;

  -- Display updated loans after applying discount
  SELECT * FROM loans;

  -- Scenario 2:
  OPEN cursor_c1;
  SET no_more_rows = FALSE;
  REPEAT
    FETCH cursor_c1 INTO v_customer_id, v_age, v_balance;
    IF NOT no_more_rows THEN
      IF v_balance > 10000 THEN
        UPDATE customers SET is_vip = 'Y' WHERE customer_id = v_customer_id;
      END IF;
    END IF;
  UNTIL no_more_rows END REPEAT;
  CLOSE cursor_c1;

  -- Display updated customers after setting VIP flag
  SELECT * FROM customers;

  -- Scenario 3:
  OPEN cursor_c2;
  SET no_more_rows = FALSE;
  REPEAT
    FETCH cursor_c2 INTO v_loan_id, v_customer_id, v_interest_rate, v_due_date;
    IF NOT no_more_rows THEN
      IF v_due_date BETWEEN CURDATE() AND CURDATE() + INTERVAL 30 DAY THEN
        SELECT CONCAT('Reminder: Loan ', v_loan_id, ' for customer ', v_customer_id, ' is due on ', v_due_date) AS reminder_message;
      END IF;
    END IF;
  UNTIL no_more_rows END REPEAT;
  CLOSE cursor_c2;
END //
DELIMITER ;

CALL update_customer_data();

