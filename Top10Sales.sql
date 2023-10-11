SELECT date, SUM(total_price) as daily_sales -- Top 10 days with most sales (profit)
FROM orders
GROUP BY date
ORDER BY daily_sales DESC
LIMIT 10;