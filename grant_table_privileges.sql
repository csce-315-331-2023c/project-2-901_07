GRANT SELECT ON customer TO public;
GRANT INSERT ON customer TO public;
GRANT UPDATE ON customer TO public;
GRANT DELETE ON customer TO public;
GRANT USAGE ON SEQUENCE customer_customer_id_seq TO public;


GRANT SELECT ON drink TO public;
GRANT INSERT ON drink TO public;
GRANT UPDATE ON drink TO public;
GRANT DELETE ON drink TO public;
GRANT USAGE ON SEQUENCE drink_drink_id_seq TO public;


GRANT SELECT ON drink_topping TO public;
GRANT INSERT ON drink_topping TO public;
GRANT UPDATE ON drink_topping TO public;
GRANT DELETE ON drink_topping TO public;
GRANT USAGE ON SEQUENCE drink_topping_drink_topping_id_seq TO public;


GRANT SELECT ON employee TO public;
GRANT INSERT ON employee TO public;
GRANT UPDATE ON employee TO public;
GRANT DELETE ON employee TO public;
GRANT USAGE ON SEQUENCE employee_employee_id_seq TO public;

GRANT SELECT ON orders TO public;
GRANT INSERT ON orders TO public;
GRANT UPDATE ON orders TO public;
GRANT DELETE ON orders TO public;
GRANT USAGE ON SEQUENCE orders_order_id_seq TO public;

GRANT SELECT ON topping TO public;
GRANT INSERT ON topping TO public;
GRANT UPDATE ON topping TO public;
GRANT DELETE ON topping TO public;
GRANT USAGE ON SEQUENCE topping_topping_id_seq TO public;

GRANT SELECT ON menu_item TO public;
GRANT INSERT ON menu_item TO public;
GRANT UPDATE ON menu_item TO public;
GRANT DELETE ON menu_item TO public;
GRANT USAGE ON SEQUENCE menu_item_menu_item_id_seq TO public;

GRANT SELECT ON ingredients TO public;
GRANT INSERT ON ingredients TO public;
GRANT UPDATE ON ingredients TO public;
GRANT DELETE ON ingredients TO public;
GRANT USAGE ON SEQUENCE ingredients_ingredients_id_seq TO public;

GRANT SELECT ON menu_ingredients_mapper TO public;
GRANT INSERT ON menu_ingredients_mapper TO public;
GRANT UPDATE ON menu_ingredients_mapper TO public;
GRANT DELETE ON menu_ingredients_mapper TO public;
GRANT USAGE ON SEQUENCE menu_ingredients_mapper_menu_ingredients_mapper_id_seq TO public;   