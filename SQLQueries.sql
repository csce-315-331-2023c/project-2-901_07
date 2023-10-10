SELECT employee_id, COUNT(order_id) as total_orders -- Employee w/ most orders
FROM orders
GROUP BY employee_id
ORDER BY total_orders DESC
LIMIT 1;



SELECT employee_id, COUNT(order_id) as total_orders -- Employee w/ least orders
FROM orders
GROUP BY employee_id
ORDER BY total_orders ASC
LIMIT 1;

SELECT SUM(total_price) as total_revenue -- Total Revenue
FROM orders;

SELECT COUNT(order_id) as total_orders -- Total amount of orders
FROM orders;

SELECT order_id, total_price -- Least expensive order possible
FROM orders
ORDER BY total_price ASC
LIMIT 1;


SELECT order_id, date, total_price -- Top 10 most expensive orders on which days
FROM orders
ORDER BY total_price DESC
LIMIT 10;


SELECT customer_id, COUNT(order_id) as order_count -- Most common customer
FROM orders
GROUP BY customer_id
ORDER BY order_count DESC
LIMIT 1;

SELECT customer_id, SUM(total_price) as total_spent -- Top 3 spending customers
FROM orders
GROUP BY customer_id
ORDER BY total_spent DESC
LIMIT 3;

SELECT order_id, date, time -- Top 10 most recent orders
FROM orders
ORDER BY date DESC, time DESC
LIMIT 10;






SELECT name, sweetness -- Top 10 sweetest drinks
FROM drink
ORDER BY sweetness DESC
LIMIT 10;


SELECT name, sweetness -- Top 10 least sweetest drinks
FROM drink
ORDER BY sweetness ASC
LIMIT 10;






SELECT name, Price -- Show all toppings
FROM Topping;





SELECT employee_id, name -- Print out Managers
FROM Employee
WHERE manager = TRUE;

SELECT employee_id, name -- Print out Employees
FROM Employee
WHERE manager = FALSE;







