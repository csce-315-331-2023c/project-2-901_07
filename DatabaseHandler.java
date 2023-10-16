import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.sql.*;

public class DatabaseHandler {
    static private Connection conn = null;
    static private String teamName = "01g";
    static private String dbName = "csce315331_"+teamName+"_db";
    static private String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
    static private dbSetup myCredentials = new dbSetup();

    static List<List<String>> toppingData;
    static List<List<String>> menuItemData;
    static List<List<String>> ingredientData;
    static List<List<String>> orderData;
    static List<List<String>> menuIngredientsMapperData;
    static List<List<String>> employeeData;
    static List<List<String>> customerData;
    static List<List<String>> drinkTypes;

    static List<String> ingredientNames;

    // Drink
    public static HashMap<String, Integer> drinkNameIdMap;
    public static HashMap<Integer, List<Integer>> drinkIngredientMap; 
    public static HashMap<String, Double> drinkPriceMap;
    public static HashMap<String, List<String>> drinkTypeMap;
    //Ingredients
    public static HashMap<String, Integer> ingredientNameIdMap;

    // drink Type
    public static Set<String> typeList;
    // Topping
    public static HashMap<String, Integer> toppingIdMap;
    public static HashMap<String, Double> toppingPriceMap;
    // Currently Use Topping and Ingredient
    public static HashMap<Integer, Integer> toppingUsed;
    public static HashMap<Integer, Integer> ingredientUsed;
    // I dont know
    public static List<List<String>> toppings;
    // List of Chosen Drink
    public static List<drinkDetailDatabase> listOrderingDrink = new ArrayList<>();
    // Customer 
    public static HashMap<String, Integer> customerNameIdMap;


    public static void setUpHashMap(){
        // Setup Topping
        toppingIdMap = new HashMap<>();
        toppingPriceMap = new HashMap<>();
        toppingUsed = new HashMap<>();
        for(List<String> topping : toppingData){
            // Setup ID for each topping
            toppingIdMap.put(topping.get(1), Integer.parseInt(topping.get(0)));
            // Setup price for each topping
            toppingPriceMap.put(topping.get(1), Double.parseDouble(topping.get(2)));
            // Initialize Topping Used
            toppingUsed.put(Integer.parseInt(topping.get(0)), 0);
        }

        // Setup List of type
        typeList = new HashSet<String>();
        drinkNameIdMap = new HashMap<>();
        drinkPriceMap = new HashMap<>();
        drinkTypeMap = new HashMap<>();
        for(List<String> drink : menuItemData){
            if (!typeList.contains(drink.get(2))){
                drinkTypeMap.put(drink.get(2), new ArrayList<>());
            }
            typeList.add(drink.get(2));
            drinkNameIdMap.put(drink.get(1), Integer.parseInt(drink.get(0)));
            drinkPriceMap.put(drink.get(1), Double.parseDouble(drink.get(3)));
            
            drinkTypeMap.get(drink.get(2)).add(drink.get(1));
        }

        // Initialize Ingredient Used
        ingredientUsed = new HashMap<>();
        // Setup ingredientNameIdMap
        ingredientNameIdMap = new HashMap<>();
        ingredientNames = new ArrayList<>();
        for (List<String> ingredient : ingredientData){
            ingredientUsed.put(Integer.parseInt(ingredient.get(0)), 0);
            ingredientNameIdMap.put(ingredient.get(1),Integer.parseInt(ingredient.get(0)));
            ingredientNames.add(ingredient.get(1));
        }
        System.out.println(drinkTypeMap);
        
        
        // Set up drinkIngredientMap
        drinkIngredientMap = new HashMap<>();
        for (List<String> DIMap : menuIngredientsMapperData){
            Integer menuItemID = Integer.parseInt(DIMap.get(0));
            Integer ingrID = Integer.parseInt(DIMap.get(1));

            if (drinkIngredientMap.containsKey(menuItemID)) {
                // If it's in the map, add the ingrID to the existing list
                drinkIngredientMap.get(menuItemID).add(ingrID);
            } else {
                // If it's not in the map, create a new list and add the ingrID
                List<Integer> ingrList = new ArrayList<>();
                ingrList.add(ingrID);
                drinkIngredientMap.put(menuItemID, ingrList);
            }
        }

        // Set up customer Id Map
        customerNameIdMap = new HashMap<>();
        for (List<String> customer : customerData){
            customerNameIdMap.put(customer.get(1), Integer.parseInt(customer.get(0)));
        }
    }

    static List<List<String>> query_SQL(String command, List<String> columnNames){
        List<List<String>> output = new ArrayList<>();
        try {
            DatabaseHandler.conn = DriverManager.getConnection(DatabaseHandler.dbConnectionString, dbSetup.user, dbSetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        try{
            Statement createStmt = conn.createStatement();
            System.out.println("Running command: " + command);
            ResultSet result = conn.createStatement().executeQuery(command);
            //System.out.println("--------------------Query Results--------------------");
            while (result.next()) {
               List<String> rowInfo = new ArrayList<>();
               for (String columnName: columnNames) {
                    rowInfo.add(result.getString(columnName));
               }
               output.add(rowInfo);
            }
            //System.out.println(output);
            //System.out.println(result);
            
        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        //closing the connection
        try {
            conn.close();
            System.out.println("Connection Closed.");
            return output;
        } catch(Exception e) {
            System.out.println("Connection NOT Closed.");
        }//end try catch
        return null;
    }


    public static boolean run_SQL_Command(String tableName, String command){
        //Building the connection with your credentials
           try {
               conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
           } catch (Exception e) {
               e.printStackTrace();
               System.err.println(e.getClass().getName()+": "+e.getMessage());
               System.exit(0);
           }
   
           System.out.println("\nOpened database successfully");
           System.out.println("Executing SQL Command: \"%s\"".formatted(command));
   
           try{
               Statement createStmt = conn.createStatement();
               boolean executed = conn.createStatement().execute(command);
               //System.out.println("COMMAND OUTPUT: " + executed);
               System.out.println("Command executed successfully \n");
           } catch (Exception e){
               e.printStackTrace();
               System.err.println(e.getClass().getName()+": "+e.getMessage());
               System.exit(0);
           }
   
           //closing the connection
           try {
               conn.close();
               System.out.println("Connection Closed.\n\n");
               return true;
           } catch(Exception e) {
               System.out.println("Connection NOT Closed.");
           }//end try catch
           return false;
       }

    public static void updateMenuItems(){
        //query menu_item table
        String queryCommand = 
        """
            SELECT *
            FROM menu_item
            ORDER BY name ASC;        
        """;
        List<String> columnNames = new ArrayList<>();
        columnNames.add("menu_item_id");
        columnNames.add("name");
        columnNames.add("type");
        columnNames.add("price");
        DatabaseHandler.menuItemData = DatabaseHandler.query_SQL(queryCommand,columnNames);
        //query menu_ingredient_mapper table
        queryCommand = 
        """
            SELECT *
            FROM menu_ingredients_mapper;
        """;
        columnNames.clear();
        columnNames.add("menu_item_id");
        columnNames.add("ingredients_id");
        DatabaseHandler.menuIngredientsMapperData = DatabaseHandler.query_SQL(queryCommand,columnNames);
        drinkNameIdMap = new HashMap<>();
        drinkPriceMap = new HashMap<>();
        drinkTypeMap = new HashMap<>();
        for(List<String> drink : menuItemData){
            if (!typeList.contains(drink.get(2))){
                drinkTypeMap.put(drink.get(2), new ArrayList<>());
            }
            drinkNameIdMap.put(drink.get(1), Integer.parseInt(drink.get(0)));
            drinkPriceMap.put(drink.get(1), Double.parseDouble(drink.get(3)));
            
            drinkTypeMap.get(drink.get(2)).add(drink.get(1));
        }
    }

    public static void updateIngredients(){
        //query ingredients table
        String queryCommand = 
        """
            SELECT *
            FROM ingredients;
        """;
        List<String> columnNames = new ArrayList<>();
        columnNames.add("ingredients_id");
        columnNames.add("name");
        columnNames.add("availability");
        DatabaseHandler.ingredientData = DatabaseHandler.query_SQL(queryCommand,columnNames);       
     
        
        ingredientUsed = new HashMap<>();
        // Setup ingredientNameIdMap
        ingredientNameIdMap = new HashMap<>();
        ingredientNames = new ArrayList<>();
        for (List<String> ingredient : ingredientData){
            ingredientUsed.put(Integer.parseInt(ingredient.get(0)), 0);
            ingredientNameIdMap.put(ingredient.get(1),Integer.parseInt(ingredient.get(0)));
            ingredientNames.add(ingredient.get(1));
        }
    }

    public static void updateToppings(){
        //query topping table
        String queryCommand = 
        """
            SELECT *
            FROM topping
            ORDER BY name ASC;        
        """;
        List<String> columnNames = new ArrayList<>();
        columnNames.add("topping_id");
        columnNames.add("name");
        columnNames.add("price");
        columnNames.add("availability");
        DatabaseHandler.toppingData = DatabaseHandler.query_SQL(queryCommand,columnNames); 
        
        // Setup Topping
        toppingIdMap = new HashMap<>();
        toppingPriceMap = new HashMap<>();
        toppingUsed = new HashMap<>();
        for(List<String> topping : toppingData){
            // Setup ID for each topping
            toppingIdMap.put(topping.get(1), Integer.parseInt(topping.get(0)));
            // Setup price for each topping
            toppingPriceMap.put(topping.get(1), Double.parseDouble(topping.get(2)));
            // Initialize Topping Used
            toppingUsed.put(Integer.parseInt(topping.get(0)), 0);
        }
    }


    public static void queryData(){
        //query topping table
        String queryCommand = 
        """
            SELECT *
            FROM topping
            ORDER BY name ASC;        
        """;
        List<String> columnNames = new ArrayList<>();
        columnNames.add("topping_id");
        columnNames.add("name");
        columnNames.add("price");
        columnNames.add("availability");
        DatabaseHandler.toppingData = DatabaseHandler.query_SQL(queryCommand,columnNames);

        //query menu_item table
        queryCommand = 
        """
            SELECT *
            FROM menu_item
            ORDER BY name ASC;        
        """;
        columnNames.clear();
        columnNames.add("menu_item_id");
        columnNames.add("name");
        columnNames.add("type");
        columnNames.add("price");
        DatabaseHandler.menuItemData = DatabaseHandler.query_SQL(queryCommand,columnNames);

        //query menu_item types
        queryCommand = 
        """
            SELECT DISTINCT type
            FROM menu_item;   
        """;
        columnNames.clear();
        columnNames.add("type");
        DatabaseHandler.drinkTypes = DatabaseHandler.query_SQL(queryCommand,columnNames);       

        //query ingredients table
        queryCommand = 
        """
            SELECT *
            FROM ingredients;
        """;
        columnNames.clear();
        columnNames.add("ingredients_id");
        columnNames.add("name");
        columnNames.add("availability");
        DatabaseHandler.ingredientData = DatabaseHandler.query_SQL(queryCommand,columnNames);

        //query orders table
        queryCommand = 
        """
            SELECT *
            FROM orders;
        """;
        columnNames.clear();
        columnNames.add("order_id");
        DatabaseHandler.orderData = DatabaseHandler.query_SQL(queryCommand,columnNames);

        //query menu_ingredient_mapper table
        queryCommand = 
        """
            SELECT *
            FROM menu_ingredients_mapper;
        """;
        columnNames.clear();
        columnNames.add("menu_item_id");
        columnNames.add("ingredients_id");
        DatabaseHandler.menuIngredientsMapperData = DatabaseHandler.query_SQL(queryCommand,columnNames);

        //query employee table
        queryCommand = 
        """
            SELECT *
            FROM employee;
        """;
        columnNames.clear();
        columnNames.add("employee_id");
        columnNames.add("manager");
        columnNames.add("name");
        DatabaseHandler.employeeData = DatabaseHandler.query_SQL(queryCommand,columnNames);
    
        //query customer table
        queryCommand = 
        """
            SELECT *
            FROM customer;
        """;
        columnNames.clear();
        columnNames.add("customer_id");
        columnNames.add("name");
        DatabaseHandler.customerData = DatabaseHandler.query_SQL(queryCommand,columnNames);
    }


    public DatabaseHandler(){
        queryData();
        setUpHashMap();
    }


}




// Sales Report
/*
    WITH DrinksInRange AS (
    SELECT d.menu_item_id
    FROM orders o
    JOIN drink d ON o.order_id = d.order_id
    WHERE o.date BETWEEN 'START' AND 'END'  -- Replace START and END with the actual dates
)

    SELECT mi.name, COUNT(dri.menu_item_id) as order_count
    FROM DrinksInRange dri
    JOIN menu_item mi ON dri.menu_item_id = mi.menu_item_id
    GROUP BY mi.name
    ORDER BY order_count DESC;
*/







// Restock Report
/*
    SELECT name, availability
    FROM ingredients
    WHERE availability < (Restock Amount);  -- replace with the amount you want to set to be the restock limit
*/






// What Sales Together
/*
    WITH PairedDrinks AS (
        SELECT
            d1.order_id,
            d1.menu_item_id AS menu_item_id1,
            d2.menu_item_id AS menu_item_id2
        FROM drink d1
        JOIN drink d2 ON d1.order_id = d2.order_id AND d1.menu_item_id < d2.menu_item_id
        JOIN orders o ON d1.order_id = o.order_id
        WHERE o.date BETWEEN 'START' AND 'END'  -- Replace START and END with the actual dates
    )

    SELECT
        p.menu_item_id1,
        mi1.name AS menu_item_name1,
        p.menu_item_id2,
        mi2.name AS menu_item_name2,
        COUNT(*) AS frequency
    FROM PairedDrinks p
    JOIN menu_item mi1 ON p.menu_item_id1 = mi1.menu_item_id
    JOIN menu_item mi2 ON p.menu_item_id2 = mi2.menu_item_id
    GROUP BY p.menu_item_id1, mi1.name, p.menu_item_id2, mi2.name
    ORDER BY frequency DESC;


*/




// Excess Report
/*
    WITH ItemSales AS (
        SELECT
            d.menu_item_id,
            COUNT(d.drink_id) as number_sold
        FROM drink d
        JOIN orders o ON d.order_id = o.order_id
        WHERE o.date >= '2022-12-31 09:00:00' AND o.date <= CURRENT_TIMESTAMP – Change date & timestamp to whatever
        GROUP BY d.menu_item_id
    ),

    IngredientUse AS (
        SELECT
            mim.ingredients_id,
            SUM(its.number_sold) as ingredients_used
        FROM ItemSales its  -- Changed alias here
        JOIN menu_ingredients_mapper mim ON its.menu_item_id = mim.menu_item_id  -- And here
        GROUP BY mim.ingredients_id
    )

    SELECT
        i.ingredients_id,
        i.name,
        i.availability,
        COALESCE(iu.ingredients_used, 0) as ingredients_used
    FROM ingredients i
    LEFT JOIN IngredientUse iu ON i.ingredients_id = iu.ingredients_id
    WHERE COALESCE(iu.ingredients_used, 0) < 0.10 * i.availability
    ORDER BY i.availability DESC;


*/