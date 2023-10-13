import java.util.ArrayList;
import java.util.List;
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
            ResultSet result = conn.createStatement().executeQuery(command);
            System.out.println("--------------------Query Results--------------------");
            while (result.next()) {
               List<String> rowInfo = new ArrayList<>();
               for (String columnName: columnNames) {
                    rowInfo.add(result.getString(columnName));
               }
               output.add(rowInfo);
            }
            System.out.println(output);
            System.out.println(result);
            
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

       

    public DatabaseHandler(){
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

        //query ingredients table
        queryCommand = 
        """
            SELECT *
            FROM ingredients
            ORDER BY name ASC;        
        """;
        columnNames.clear();
        columnNames.add("ingredients_id");
        columnNames.add("name");
        columnNames.add("availability");
        DatabaseHandler.ingredientData = DatabaseHandler.query_SQL(queryCommand,columnNames);

    }
}



/*
 * 
 * 
 *  WITH DrinksInRange AS (
    SELECT d.menu_item_id
    FROM orders o
    JOIN drink d ON o.order_id = d.order_id
    WHERE o.date BETWEEN 'start_date' AND 'end_date'  -- replace start_date and end_date with actual dates
)

    SELECT mi.name, COUNT(dri.menu_item_id) as order_count
    FROM DrinksInRange dri
    JOIN menu_item mi ON dri.menu_item_id = mi.menu_item_id
    GROUP BY mi.name
    ORDER BY order_count DESC;
 * 
 * 
 * 
 * 
 */