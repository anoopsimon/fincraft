-- Insert a customer
INSERT INTO customer (id, name, email, phone_number) VALUES (:id, :name, :email, :phoneNumber);

-- Insert an account
INSERT INTO account (id, customer_id, account_type, balance) VALUES (:id, :customerId, :accountType, :balance);

-- Insert a credit card
INSERT INTO credit_card (id, customer_id, credit_limit, current_outstanding) VALUES (:id, :customerId, :creditLimit, :currentOutstanding);
