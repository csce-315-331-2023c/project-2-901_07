import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import javax.naming.spi.DirStateFactory.Result;
import java.util.Set;
import javax.swing.border.*;
import javax.tools.JavaFileManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI{
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    ManagerView managerView = new ManagerView();
    CardLayout bottomPanelCardLayout, centerPanelCardLayout;
    
    JButton checkoutButton, transactionHistoryButton, trendsButton, inventoryButton;
    JButton switchViewButton, toGoButton, addCustomerButton, totalChargeButton, ticketsButton;
    
    JLabel currentViewLabel;
    
    JPanel centerPanel, rightPanel, bottomPanel;
    JPanel homePage, inventoryPage;

    public static JPanel checkoutPanel;
    public static Double totalPrice = 0.0;
    public static HashMap<String, Integer> drinkNameIdMap = new HashMap<>();
    public static HashMap<Integer, List<Integer>> drinkIngredientMap = new HashMap<>(); 
    public static HashMap<String, Integer> toppingIdMap = new HashMap<>();
    public static HashMap<Integer, Integer> toppingUsed = new HashMap<>();
    public static HashMap<Integer, Integer> ingredientUsed = new HashMap<>();
    public static HashMap<String, HashMap<String, Double>> drinkPrices = new HashMap<>();
    public static List<List<String>> toppings = new ArrayList<>();
    public static Integer nextOrderID;
    public static String employee_id;

    String current_view = "Cashier";
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // Bottom Horizontal Bar
    // Checkout + Transaction History + Trends + Availability
    public JPanel bottomPanel() {
        checkoutPanel = new JPanel(new GridLayout(10, 1, 30, 10));
        checkoutPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        checkoutPanel.add(new JLabel("List of drinks"), BorderLayout.LINE_START);
        // Bottom panel
        int panelHeight = screenSize.height / 10;
        int panelWidth = 0; // value does not matter
        //bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        //bottomPanel.setBackground(Color.gray);
        bottomPanelCardLayout = new CardLayout();
        bottomPanel = new JPanel(bottomPanelCardLayout);
        bottomPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        //cashier view bottom panel
        JPanel cashierView = new JPanel(new GridLayout(1, 5, 30, 10));
        cashierView.setBorder(new EmptyBorder(20, 20, 20, 20));
        cashierView.add(checkout_button());
        cashierView.add(new JLabel());
        cashierView.add(new JLabel());
        cashierView.add(new JLabel());
        //manager view bottom panel
        JPanel managerView = new JPanel(new GridLayout(1, 5, 30, 10));
        managerView.setBorder(new EmptyBorder(20, 20, 20, 20));
        managerView.add(checkout_button());
        managerView.add(transactionHistory_button());
        managerView.add(trends_button());
        managerView.add(inventory_button());

        bottomPanel.add(cashierView, "Cashier View");
        bottomPanel.add(managerView, "Manager View");
        employee_id = JOptionPane.showInputDialog("Enter Employee ID:");
        return bottomPanel;
    }
    
    // 1. Checkout Button
    public JButton checkout_button() {
        checkoutButton = new JButton("Checkout");
        checkoutButton.setMargin(new Insets(50, 50, 50, 50));
        checkoutButton.setFont(new Font("Calibri", Font.BOLD, 16));
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the category handler and pass the selected category.
                checkoutHandler.checkoutFrame_();
            }
        });
        return checkoutButton;
    }

    // 2. Transaction Button
    public JButton transactionHistory_button() {
        transactionHistoryButton = new JButton("Transaction");
        transactionHistoryButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return transactionHistoryButton;
    }

    // 3. Trends Button
    public JButton trends_button() {
        trendsButton = new JButton("Trends");
        trendsButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return trendsButton;
    }

    // 4. Availability Button
    public JButton inventory_button() {
        inventoryButton = new JButton("Inventory");
        inventoryButton.setFont(new Font("Calibri", Font.BOLD, 16));
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("inventoryButton clicked");
                centerPanelCardLayout.show(centerPanel, "Inventory Page");
            }
        });
        return inventoryButton;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    // Right Vertical Bar
    // Switch View + To Go + Charge Total + Tickets
    public JPanel rightPanel() {
        // Right panel
        int panelHeight = 0; // value does not matter
        int panelWidth = screenSize.width / 6;
        JPanel rightPanel = new JPanel(new GridLayout(0, 1, 20, 20));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        rightPanel.setBackground(Color.cyan);
        rightPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        currentViewLabel = new JLabel("Current View: " + current_view);
        currentViewLabel.setFont(new Font("Calibri ", Font.BOLD, 15));
        currentViewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        rightPanel.add(currentViewLabel);
        rightPanel.add(switchView_button());
        rightPanel.add(toGo_button());
        rightPanel.add(addCustomer_button());
        rightPanel.add(totalCharge_button());
        rightPanel.add(tickets_button());
        return rightPanel;
    }

    // 1. Switch View 
    public JButton switchView_button() {
        switchViewButton = new JButton("Switch View");
        switchViewButton.setBounds(200, 100, 100, 50);
        switchViewButton.setFont(new Font("Calibri", Font.BOLD, 16));
        switchViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("switchViewButton clicked");
                if (current_view == "Manager"){
                    current_view = "Cashier";
                    employee_id = JOptionPane.showInputDialog("Enter Employee ID:");
                }else{
                    current_view = "Manager";
                }
                bottomPanelCardLayout.next(bottomPanel);
                centerPanelCardLayout.show(centerPanel, "Home Page");
                currentViewLabel.setText("Current View: " + current_view);
            }
        });
        return switchViewButton;
    }

    // 2. To Go 
    public JButton toGo_button() {
        toGoButton = new JButton("To Go");
        toGoButton.setBounds(200, 100, 100, 50);
        toGoButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return toGoButton;
    }

    // 3. Add Customer 
    public JButton addCustomer_button() {
        addCustomerButton = new JButton("Add Customer");
        addCustomerButton.setBounds(200, 100, 100, 50);
        addCustomerButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return addCustomerButton;
    }

    // 4. Total Charge 
    public JButton totalCharge_button() {
        totalChargeButton = new JButton("Total Charge");
        totalChargeButton.setBounds(200, 100, 100, 50);
        totalChargeButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return totalChargeButton;
    }

    // 5. Tickets
    public JButton tickets_button() {
        ticketsButton = new JButton("Tickets");
        ticketsButton.setBounds(200, 100, 100, 50);
        ticketsButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return ticketsButton;
    }

    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public JPanel homePage() {
        int panelHeight = 300;
        int panelWidth = screenSize.width - (screenSize.width / 6);
        JPanel homePagePanel = new JPanel(new GridLayout(3, 4, 30, 30));
        homePagePanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        homePagePanel.setBackground(Color.white);
        homePagePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        String[] categories = {"Milk Tea", "Fresh Milk", "Ice Blend", "Fruit Tea", "Mojito", "Creama"};

        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setBackground(Color.gray);
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Call the category handler and pass the selected category.
                    JPanel tempPanel = categoryHandler.categoryHandlerPanel(category);
                    checkoutPanel.add(tempPanel);
                }
            });
            homePagePanel.add(categoryButton);
        }
        return homePagePanel;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // Center Horizontal Bar
    // Milk Tea
    
    public JPanel centerPanel() {
        // Center panel
        int panelHeight = 300;
        int panelWidth = screenSize.width - (screenSize.width / 4);
        centerPanelCardLayout = new CardLayout();
        centerPanel = new JPanel(centerPanelCardLayout);
        centerPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        //inventory page panel
        inventoryPage = new JPanel();
        inventoryPage = ManagerView.inventoryPage();

        centerPanel.add(homePage(), "Home Page");
        centerPanel.add(inventoryPage, "Inventory Page");

        return centerPanel;
    }

    

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public GUI() {
        // FRAMES TUTORIAL
        JFrame frame = new JFrame();

        frame.setTitle("Sharetea - Glory of Taiwan!"); // sets title of frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
        // frame.setResizable(false); //prevent frame from being resized
        frame.setSize(screenSize.width, screenSize.height); // sets the x-dimension, and y-dimension of frame
        ImageIcon image = new ImageIcon("logo.png");
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(255, 255, 255));

        frame.setLayout(new BorderLayout());
        frame.add(rightPanel(), BorderLayout.EAST);
        frame.add(bottomPanel(), BorderLayout.SOUTH);
        frame.add(centerPanel(), BorderLayout.CENTER);

        frame.setVisible(true); // make frame visible
        // END OF FRAMES TUTORIAL

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public static List<List<String>> query(String tableName, List<String> columnNames){
        //Building the connection with your credentials
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void setupDatabase(){
        HashMap<String, Double> milkTea = new HashMap<>();
        HashMap<String, Double> freshMilk = new HashMap<>();
        HashMap<String, Double> iceBlend = new HashMap<>();
        HashMap<String, Double> fruitTea = new HashMap<>();
        HashMap<String, Double> mojito = new HashMap<>();
        HashMap<String, Double> creama = new HashMap<>();

        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("type");
        columnNames.add("price");
        columnNames.add("menu_item_id");
        List<List<String>> drinks = GUI.query("menu_item", columnNames);

        for (List<String> drink : drinks) {
            String drinkName = drink.get(0);
            String drinkType = drink.get(1);
            Double drinkPrice = Double.parseDouble(drink.get(2));
            Integer drinkID = Integer.parseInt(drink.get(3));
            GUI.drinkNameIdMap.put(drinkName, drinkID);
            switch(drinkType) {
                case "Milk Tea":
                    milkTea.put(drinkName, drinkPrice);
                    break;
                case "Fresh Milk":
                    freshMilk.put(drinkName, drinkPrice);
                    break;
                case "Ice Blend":
                    iceBlend.put(drinkName, drinkPrice);
                    break;
                case "Fruit Tea":
                    fruitTea.put(drinkName, drinkPrice);
                    break;
                case "Mojito":
                    mojito.put(drinkName, drinkPrice);
                    break;
                case "Creama":
                    creama.put(drinkName, drinkPrice);
                    break;
            }
        }
        drinkPrices.put("Milk Tea", milkTea);
        drinkPrices.put("Fresh Milk", freshMilk);
        drinkPrices.put("Ice Blend", iceBlend);
        drinkPrices.put("Fruit Tea", fruitTea);
        drinkPrices.put("Mojito", mojito);
        drinkPrices.put("Creama", creama);
        
        /////////////////////////////////////////////////////////////
        columnNames.clear();
        columnNames.add("menu_item_id");
        columnNames.add("ingredients_id");
        List<List<String>> drinkIngredientMappers = GUI.query("menu_ingredients_mapper", columnNames);

        for (List<String> DIMap : drinkIngredientMappers){
            Integer menuItemID = Integer.parseInt(DIMap.get(0));
            Integer ingrID = Integer.parseInt(DIMap.get(1));

            if (GUI.drinkIngredientMap.containsKey(menuItemID)) {
                // If it's in the map, add the ingrID to the existing list
                GUI.drinkIngredientMap.get(menuItemID).add(ingrID);
            } else {
                // If it's not in the map, create a new list and add the ingrID
                List<Integer> ingrList = new ArrayList<>();
                ingrList.add(ingrID);
                GUI.drinkIngredientMap.put(menuItemID, ingrList);
            }
        }

        ////////////////////////////////////////////////////////////
        columnNames.clear();
        columnNames.add("name");
        columnNames.add("price");
        columnNames.add("topping_id");
        toppings = GUI.query("topping", columnNames);
        for (List<String> topping : GUI.toppings){
            String toppingName = topping.get(0);
            Integer toppingID = Integer.parseInt(topping.get(2));
            GUI.toppingIdMap.put(toppingName, toppingID);
            toppingUsed.put(toppingID, 0);
        }

        ////////////////////////////////////////////////////////////
        columnNames.clear();
        columnNames.add("ingredients_id");
        List<List<String>> ingredients = GUI.query("ingredients", columnNames);
        for (List<String> ingredient : ingredients){
            GUI.ingredientUsed.put(Integer.parseInt(ingredient.get(0)), 0);
        }

        columnNames.clear();
        columnNames.add("order_id");
        List<List<String>> orderList = GUI.query("orders", columnNames);
        nextOrderID = orderList.size();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean run_SQL_Command(String tableName, String command){
        //Building the connection with your credentials
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


    public static void main(String[] args) {
        setupDatabase();
        new GUI(); // Create an instance of the Main class to initialize the UI
    }
}
