import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.naming.spi.DirStateFactory.Result;
import java.util.Set;

public class ManagerView {
    // Class contains all methods associated with the pages for the Manager view

    public static JPanel inventoryPage(JPanel inventoryPage){
        //Method contains commands to format inventory page
        inventoryPage.setBackground(Color.red);
        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("price");
        List<List<String>> toppings = query("topping", columnNames);
        for (List<String> topping : toppings) {
          JButton toppingButton = new JButton( "<html>%s<br>%s</html>".formatted(topping.get(0),topping.get(1)));
          inventoryPage.add(toppingButton);
        }
        return inventoryPage;
    }
    
    public static List<List<String>> query(String tableName, List<String> columnNames){
     //Building the connection with your credentials
        System.out.println("RANDO");
        Connection conn = null;
        String teamName = "01g";
        String dbName = "csce315331_"+teamName+"_db";
        // "csce315331_01g_db"
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup();

        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");

        try{
            Statement createStmt = conn.createStatement();
            String selectTable = "SELECT * FROM "+tableName+";";
            ResultSet result = conn.createStatement().executeQuery(selectTable);
            List<List<String>> output = new ArrayList<>();
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
            return output;
        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        //closing the connection
        try {
            conn.close();
            System.out.println("Connection Closed.");
        } catch(Exception e) {
            System.out.println("Connection NOT Closed.");
        }//end try catch
        return null;
    }



     ManagerView(){
     //Building the connection with your credentials
     }
}

