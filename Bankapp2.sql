USE enterprise_bank;

INSERT INTO roles(name) VALUES ('ADMIN'), ('EMPLOYEE'), ('CUSTOMER');

SELECT *
FROM transactions
WHERE created_at >= CURDATE()
AND created_at < CURDATE() + INTERVAL 1 DAY;


-- Default admin user (password: admin)
INSERT INTO users(username,password_hash,role_id)
VALUES ('jega', SHA2('jega@123',256), 1);
SELECT IFNULL(SUM(amount), 0)
FROM transactions
WHERE from_acc = 3
AND DATE(created_at) = CURDATE();
SELECT id, from_acc, to_acc, amount, created_at
FROM transactions
WHERE amount > 50000;
SELECT c.id, c.name, a.balance
FROM customers c
JOIN accounts a ON c.id = a.customer_id
ORDER BY a.balance DESC
LIMIT 10;
INSERT INTO report_audit (admin_id, report_name)
VALUES (?, ?);
INSERT INTO customers (name, city, mobile)
VALUES ('Ravi', 'Chennai', '9999999999');

INSERT INTO accounts (customer_id, balance)
VALUES (1, 150000);

INSERT INTO transactions (from_acc, to_acc, amount)
VALUES (1, 2, 5000),
       (2, 1, 200000);

SELECT daily_limit
FROM accounts
WHERE id = 1;
SELECT from_acc, to_acc, amount, created_at
FROM transactions
WHERE DATE(created_at) = CURDATE();


SELECT *
FROM transactions
WHERE DATE(created_at) = CURDATE();
SELECT * FROM transactions WHERE amount > 100000;

SELECT from_acc, COUNT(*) as txn_count, SUM(amount) as total
FROM transactions
GROUP BY from_acc
HAVING total > 100000;
SELECT acc_id, balance
FROM accounts
ORDER BY balance DESC
LIMIT 10;
SELECT c.id, c.name, a.balance
FROM customers c
JOIN accounts a ON c.id = a.customer_id
ORDER BY a.balance DESC
LIMIT 10;

INSERT INTO accounts (customer_id, balance)
VALUES (1, 50000), (2, 150000), (3, 75000);
INSERT INTO transactions (from_acc, to_acc, amount, created_at)
VALUES 
(1, 2, 10000, NOW()),
(2, 3, 50000, NOW()),
(3, 1, 25000, NOW());

DROP TABLE transactions;
DROP TABLE accounts;
DROP TABLE customers;
SELECT * FROM report_audit;
SELECT * FROM users;
SELECT * FROM customers;
SELECT * FROM audit_logs;
SELECT * FROM roles;
SELECT * FROM accounts;
SELECT * FROM transactions;
SELECT COUNT(*) FROM transactions;

-- =========================
-- Sample Customers
-- =========================
INSERT INTO customers (name, city, mobile) VALUES
('Alice', 'Chennai', '9000000001'),
('Bob', 'Madurai', '9000000002'),
('Charlie', 'Coimbatore', '9000000003'),
('David', 'Trichy', '9000000004'),
('Eva', 'Salem', '9000000005'),
('Frank', 'Vellore', '9000000006'),
('Grace', 'Erode', '9000000007'),
('Hannah', 'Tirunelveli', '9000000008'),
('Ivy', 'Tuticorin', '9000000009'),
('Jack', 'Nagapattinam', '9000000010');

-- =========================
-- Sample Accounts
-- =========================
INSERT INTO accounts (customer_id, balance) VALUES
(1, 50000),
(2, 150000),
(3, 75000),
(4, 30000),
(5, 120000),
(6, 90000),
(7, 60000),
(8, 80000),
(9, 110000),
(10, 40000);

-- =========================
-- Sample Transactions (20)
-- =========================
INSERT INTO transactions (from_acc, to_acc, amount, created_at) VALUES
(1, 2, 5000, NOW() - INTERVAL 1 DAY),
(2, 3, 10000, NOW() - INTERVAL 1 DAY),
(3, 4, 7000, NOW() - INTERVAL 1 DAY),
(4, 5, 2000, NOW() - INTERVAL 1 DAY),
(5, 6, 15000, NOW() - INTERVAL 1 DAY),
(6, 7, 5000, NOW() - INTERVAL 1 DAY),
(7, 8, 8000, NOW() - INTERVAL 1 DAY),
(8, 9, 12000, NOW() - INTERVAL 1 DAY),
(9, 10, 3000, NOW() - INTERVAL 1 DAY),
(10, 1, 4000, NOW() - INTERVAL 1 DAY),
(1, 3, 6000, NOW() - INTERVAL 2 DAY),
(2, 4, 7000, NOW() - INTERVAL 2 DAY),
(3, 5, 5000, NOW() - INTERVAL 2 DAY),
(4, 6, 3000, NOW() - INTERVAL 2 DAY),
(5, 7, 8000, NOW() - INTERVAL 2 DAY),
(6, 8, 9000, NOW() - INTERVAL 2 DAY),
(7, 9, 10000, NOW() - INTERVAL 2 DAY),
(8, 10, 2000, NOW() - INTERVAL 2 DAY),
(9, 1, 4000, NOW() - INTERVAL 2 DAY),
(10, 2, 3000, NOW() - INTERVAL 2 DAY);
