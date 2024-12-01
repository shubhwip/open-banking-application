USE oba;

CREATE TABLE Transaction (
    id VARCHAR(36) PRIMARY KEY,
    type VARCHAR(50),
    date DATETIME,
    accountNumber VARCHAR(50),
    currency VARCHAR(3),
    amount DECIMAL(10, 2),
    merchantName VARCHAR(100),
    merchantLogo VARCHAR(255)
);

INSERT INTO Transaction (id, type, date, accountNumber, currency, amount, merchantName, merchantLogo)
VALUES
    (UUID(), 'DEBIT', '2024-12-01 10:00:00', '1234567890', 'USD', 50.00, 'Coffee Shop', 'coffee_shop_logo.png'),
    (UUID(), 'CREDIT', '2024-12-01 14:30:00', '1234567890', 'USD', 1000.00, 'Salary', 'company_logo.png'),
    (UUID(), 'DEBIT', '2024-12-02 09:15:00', '1234567890', 'USD', 25.50, 'Grocery Store', 'grocery_store_logo.png');