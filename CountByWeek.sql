SELECT DATE_TRUNC('week', date) AS week_start, COUNT(order_id) AS weekly_orders
FROM orders
GROUP BY DATE_TRUNC('week', date)
ORDER BY week_start;