SELECT date, SUM(total_price) as daily_sales -- Top 15 days with most sales (profit)
FROM orders
GROUP BY date
ORDER BY daily_sales DESC
LIMIT 15;



SELECT employee_id, COUNT(order_id) as total_orders -- Top 10 employees w/ most orders
FROM orders
GROUP BY employee_id
ORDER BY total_orders DESC
LIMIT 10;



SELECT name, sweetness -- Top 10 sweetest drinks
FROM drink
ORDER BY sweetness DESC
LIMIT 10;



SELECT name, sweetness -- Top 10 sweetest drinks
FROM drink
ORDER BY sweetness ASC
LIMIT 10;