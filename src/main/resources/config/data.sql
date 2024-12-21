-- Insert a customer
INSERT INTO customer (name, email, phone_number) VALUES (:name, :email, :phoneNumber);

-- Insert an account
INSERT INTO account (customer_id, account_type, balance) VALUES (:customerId, :accountType, :balance);

-- Insert a credit card
INSERT INTO credit_card (customer_id, credit_limit, current_outstanding) VALUES (:customerId, :creditLimit, :currentOutstanding);
