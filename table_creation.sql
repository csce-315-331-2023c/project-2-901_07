CREATE TABLE employee(
employee_id SERIAL PRIMARY KEY,
manager BOOLEAN,
name VARCHAR(255)
);

CREATE TABLE customer(
customer_id SERIAL PRIMARY KEY,
name VARCHAR(255)
);

CREATE TABLE orders(
order_id SERIAL PRIMARY KEY,
customer_id INT REFERENCES customer(customer_id),
employee_id INT REFERENCES employee(employee_id),
date DATE,
total_price FLOAT,
time TIME
);

CREATE TABLE drink(
drink_id SERIAL PRIMARY KEY,
name VARCHAR(255),
type VARCHAR(255),
order_id INT REFERENCES orders(order_id),
sweetness FLOAT,
price FLOAT,
ice_level VARCHAR(255)
);


CREATE TABLE topping(
topping_id SERIAL PRIMARY KEY,
name VARCHAR(255),
price FLOAT
);

CREATE TABLE drink_topping(
drink_topping_id SERIAL PRIMARY KEY,
drink_id INT REFERENCES drink(drink_id),
topping_id INT REFERENCES topping(topping_id)
);