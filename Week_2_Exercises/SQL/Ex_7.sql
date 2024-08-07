CREATE TABLE customers (
  customer_id INT PRIMARY KEY,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  date_of_birth DATE
);

CREATE TABLE employees (
  employee_id INT PRIMARY KEY,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  hire_date DATE,
  salary DECIMAL(10, 2)
);

CREATE TABLE accounts (
  account_id INT PRIMARY KEY,
  customer_id INT,
  account_type VARCHAR(50),
  balance DECIMAL(10, 2)
);

DELIMITER //

CREATE PACKAGE CustomerManagement AS
  PROCEDURE AddCustomer(p_first_name VARCHAR(50), p_last_name VARCHAR(50), p_date_of_birth DATE);
  PROCEDURE UpdateCustomer(p_customer_id INT, p_first_name VARCHAR(50), p_last_name VARCHAR(50));
  FUNCTION GetCustomerBalance(p_customer_id INT) RETURNS DECIMAL(10, 2);
END //

CREATE PACKAGE BODY CustomerManagement AS
  PROCEDURE AddCustomer(p_first_name VARCHAR(50), p_last_name VARCHAR(50), p_date_of_birth DATE)
  IS
  BEGIN
    INSERT INTO customers (first_name, last_name, date_of_birth)
    VALUES (p_first_name, p_last_name, p_date_of_birth);
  END;

  PROCEDURE UpdateCustomer(p_customer_id INT, p_first_name VARCHAR(50), p_last_name VARCHAR(50))
  IS
  BEGIN
    UPDATE customers
    SET first_name = p_first_name, last_name = p_last_name
    WHERE customer_id = p_customer_id;
  END;

  FUNCTION GetCustomerBalance(p_customer_id INT)
  RETURNS DECIMAL(10, 2)
  IS
    v_balance DECIMAL(10, 2) := 0;
  BEGIN
    SELECT SUM(balance) INTO v_balance
    FROM accounts
    WHERE customer_id = p_customer_id;
    RETURN v_balance;
  END;
END //

CREATE PACKAGE EmployeeManagement AS
  PROCEDURE HireEmployee(p_first_name VARCHAR(50), p_last_name VARCHAR(50), p_hire_date DATE, p_salary DECIMAL(10, 2));
  PROCEDURE UpdateEmployee(p_employee_id INT, p_salary DECIMAL(10, 2));
  FUNCTION CalculateAnnualSalary(p_employee_id INT) RETURNS DECIMAL(10, 2);
END //

CREATE PACKAGE BODY EmployeeManagement AS
  PROCEDURE HireEmployee(p_first_name VARCHAR(50), p_last_name VARCHAR(50), p_hire_date DATE, p_salary DECIMAL(10, 2))
  IS
  BEGIN
    INSERT INTO employees (first_name, last_name, hire_date, salary)
    VALUES (p_first_name, p_last_name, p_hire_date, p_salary);
  END;

  PROCEDURE UpdateEmployee(p_employee_id INT, p_salary DECIMAL(10, 2))
  IS
  BEGIN
    UPDATE employees
    SET salary = p_salary
    WHERE employee_id = p_employee_id;
  END;

  FUNCTION CalculateAnnualSalary(p_employee_id INT)
  RETURNS DECIMAL(10, 2)
  IS
    v_salary DECIMAL(10, 2);
  BEGIN
    SELECT salary INTO v_salary
    FROM employees
    WHERE employee_id = p_employee_id;
    RETURN v_salary * 12;
  END;
END //

CREATE PACKAGE AccountOperations AS
  PROCEDURE OpenAccount(p_customer_id INT, p_account_type VARCHAR(50), p_initial_balance DECIMAL(10, 2));
  PROCEDURE CloseAccount(p_account_id INT);
  FUNCTION GetTotalCustomerBalance(p_customer_id INT) RETURNS DECIMAL(10, 2);
END //

CREATE PACKAGE BODY AccountOperations AS
  PROCEDURE OpenAccount(p_customer_id INT, p_account_type VARCHAR(50), p_initial_balance DECIMAL(10, 2))
  IS
  BEGIN
    INSERT INTO accounts (customer_id, account_type, balance)
    VALUES (p_customer_id, p_account_type, p_initial_balance);
  END;

  PROCEDURE CloseAccount(p_account_id INT)
  IS
  BEGIN
    DELETE FROM accounts WHERE account_id = p_account_id;
  END;

  FUNCTION GetTotalCustomerBalance(p_customer_id INT)
  RETURNS DECIMAL(10, 2)
  IS
    v_total_balance DECIMAL(10, 2) := 0;
  BEGIN
    SELECT SUM(balance) INTO v_total_balance
    FROM accounts
    WHERE customer_id = p_customer_id;
    RETURN v_total_balance;
  END;
END //

DELIMITER ;
