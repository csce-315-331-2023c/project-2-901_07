SELECT EXTRACT(HOUR FROM time::time) AS hour, COUNT(order_id) AS total_orders -- Sum of count grouped by hour
FROM orders
GROUP BY EXTRACT(HOUR FROM time::time)
ORDER BY hour;