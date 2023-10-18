import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** 
* The checkoutHandler class manages the checkout process, allows users to add and remove drinks,
* update toppings and ingredients, and finalize their order.* 
* @author Quy Van
*/

public class checkoutHandler {
    private JFrame checkoutFrame;
    private JPanel contentPanel;

    private String customerName = null;
    private Integer customerID;
    private Integer orderID;
    private double totalCost;


    /**
     * Create a Frame to contain all the Panel created by populateDrinkOrder
     * and a button to submit the final order. If the button is clicked, the order
     * will be placed. Moreover, the database of topping, ingredient availability,
     * and order will be updated.
    */

    public void checkoutFrame_() {
        checkoutFrame = new JFrame();
        checkoutFrame.setTitle("Drink Customization Panel");
        checkoutFrame.setSize(GUI.screenSize.width, GUI.screenSize.height);
        checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkoutFrame.setLayout(new BorderLayout());
        checkoutFrame.setVisible(true);

        contentPanel = new JPanel(new GridLayout(0, 5, 30, 10));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        populateDrinkOrder();

        JButton payButton = new JButton("Insert or Tap Card");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customerName == null){
                    customerName = JOptionPane.showInputDialog(checkoutFrame, "Enter Customer Name:");
                    if(customerName == null){
                        return;
                    }
                }

                // INSERT Customer
                try {
                    String command = """
                    INSERT INTO customer (name)
                    VALUES ('%s')
                    RETURNING customer_id;
                    """.formatted(customerName);
                    List<String> tempColumn = new ArrayList<>();
                    tempColumn.add("customer_id");
                    List<List<String>> tempID = DatabaseHandler.query_SQL(command, tempColumn);
                    for (List<String> id : tempID){
                        customerID = Integer.parseInt(id.get(0));
                    }
                } catch (NumberFormatException e_2) {
                    System.err.println("ERROR");
                }


                // Call the category handler and pass the selected category.
                for (drinkDetailDatabase drinkDetail : DatabaseHandler.listOrderingDrink){
                    // Get the drink details from the current drinkDetailDatabase object
                    updateToppingsAndIngredients(drinkDetail);
                }
                
                // Update topping availability
                for (Integer toppingID : DatabaseHandler.toppingUsed.keySet()){
                    // Call the category handler and pass the selected category.
                    if (DatabaseHandler.toppingUsed.get(toppingID) != 0) {
                        try {
                            boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("topping", 
                            """
                            UPDATE topping SET availability = availability - %d
                            WHERE topping_id = '%d';
                            """.formatted(DatabaseHandler.toppingUsed.get(toppingID), toppingID));
                        } catch (NumberFormatException e_2) {
                            System.err.println("ERROR");
                        }
                    }
                }
                
                // UPDATE Ingredient Availability
                for (Integer ingredientID : DatabaseHandler.ingredientUsed.keySet()){
                    // Call the category handler and pass the selected category.
                    if (DatabaseHandler.ingredientUsed.get(ingredientID) != 0) {
                        try {
                            boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("ingredients", 
                            """
                            UPDATE ingredients SET availability = availability - %d
                            WHERE ingredients_id = '%d';
                            """.formatted(DatabaseHandler.ingredientUsed.get(ingredientID), ingredientID));
                        } catch (NumberFormatException e_2) {
                            System.err.println("ERROR");
                        }
                    }
                }


                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedDate = localDate.format(dateFormatter);
                String formattedTime = localTime.format(timeFormatter);
                
                // Insert Order
                try {
                    String command = (
                    """
                    INSERT INTO orders (customer_id, employee_id, date, total_price, time)
                    VALUES ('%d', '%d', '%s', '%f', '%s')
                    RETURNING order_id;
                    """.formatted(customerID, Integer.parseInt(GUI.employee_id), formattedDate, totalCost, formattedTime));
                    List<String> tempColumn = new ArrayList<>();
                    tempColumn.add("order_id");
                    List<List<String>> tempID = DatabaseHandler.query_SQL(command, tempColumn);
                    for (List<String> id : tempID){
                        orderID = Integer.parseInt(id.get(0));
                    }
                } catch (NumberFormatException e_2) {
                    System.err.println("ERROR");
                }
                

                for (drinkDetailDatabase drinkDetail : DatabaseHandler.listOrderingDrink){
                    for (HashMap<String,Integer> topping : drinkDetail.toppingList_){
                        // Call the category handler and pass the selected category.
                        try {
                            String toppingName = topping.keySet().toArray()[0].toString();
                            Integer toppingID = DatabaseHandler.toppingIdMap.get(toppingName);
                            Integer drinkID = drinkDetail.drinkID_;
                            boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("drink_topping", 
                            """
                            INSERT INTO drink_topping (drink_id, topping_id)
                            VALUES ('%d', '%d');
                            """.formatted(drinkID, toppingID));
                        } catch (NumberFormatException e_2) {
                            System.err.println("ERROR");
                        }
                    }
                    try {
                        boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("drink_topping", 
                        """
                        INSERT INTO drink (menu_item_id, order_id, sweetness, price, ice_level)
                        VALUES ('%d', '%d', '%d', '%f', '%s');
                        """.formatted(drinkDetail.drinkID_, orderID, Integer.parseInt(drinkDetail.sugarLevel_), drinkDetail.drinkPrice_, drinkDetail.iceLevel_));
                    } catch (NumberFormatException e_2) {
                        System.err.println("ERROR");
                    }
                }
                
                                                                                   
                
                // Reset database
                for (Integer ingredientID : DatabaseHandler.ingredientUsed.keySet()){
                    DatabaseHandler.ingredientUsed.put(ingredientID, 0);
                }
                for (Integer toppingID : DatabaseHandler.toppingUsed.keySet()){
                    DatabaseHandler.toppingUsed.put(toppingID, 0);
                }
                totalCost = 0;
                DatabaseHandler.listOrderingDrink.clear();
                DatabaseHandler.queryData();
                DatabaseHandler.setUpHashMap();

                checkoutFrame.dispose();
            }
        });
        checkoutFrame.add(contentPanel, BorderLayout. PAGE_START);
        checkoutFrame.add(payButton, BorderLayout.EAST);
    }

    /**
     * This function create multiple Panel. Each Panel contains Detail of each Drink Customization
     * (Sugar Level, Ice Level, Toppings, and Total Cost of this Drink) and a button to remove 
     * this order as the customer wishs. 
    */
    private void populateDrinkOrder() {
        // Create a StringBuilder to collect all order details
        List<String> orderDetails = new ArrayList<String>();
        // Initialize total cost
        totalCost = 0.0;
    
        for (int i = 0; i < DatabaseHandler.listOrderingDrink.size(); i++) {
            final int drinkIndex = i; // Create a final variable for the current drink index
    
            // Get the drink details from the current drinkDetailDatabase object
            drinkDetailDatabase drinkDetails = DatabaseHandler.listOrderingDrink.get(i);
            String drinkName = drinkDetails.drinkName_;
            String iceLevel = drinkDetails.iceLevel_;
            String sugarLevel = drinkDetails.sugarLevel_;
            Double drinkPrice = drinkDetails.drinkPrice_;
            Double drinkTotalPrice = drinkDetails.totalPrice_;
            List<HashMap<String, Integer>> toppingList = drinkDetails.toppingList_;
    
            // Create a new panel for each drink to hold the details and remove button
            JPanel drinkPanel = new JPanel();
            drinkPanel.setLayout(new BoxLayout(drinkPanel, BoxLayout.Y_AXIS));
    
            // Add the current drink details to the order
            orderDetails.add(i + ". Drink: " + drinkName + " ($" + DatabaseHandler.drinkPriceMap.get(drinkName) +")");
            orderDetails.add("Ice Level: " + iceLevel);
            orderDetails.add("Sugar Level: " + sugarLevel);
            for (HashMap<String, Integer> topping : toppingList){
                String toppingName = topping.keySet().toArray()[0].toString();
                orderDetails.add(toppingName + "($" + DatabaseHandler.toppingPriceMap.get(toppingName) + ")  x" + topping.get(toppingName));
            }
            orderDetails.add("Total Price: $" + String.format("%.2f", drinkTotalPrice));

            // Create a Remove button for the current drink
            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle the removal of the selected drink
                    DatabaseHandler.listOrderingDrink.remove(drinkIndex);
                    contentPanel.remove(drinkPanel); // Remove the panel from the contentPanel
                    contentPanel.revalidate();
                    contentPanel.repaint();
                    updateTotalCost(); // Update the total cost
                }
            });
    
            // Add the Remove button to the drink panel
            drinkPanel.add(removeButton);
        
            // Update the text fields with the order and total cost
            for (String line : orderDetails) {
                JLabel orderText = new JLabel(line);    
                drinkPanel.add(orderText);
            }
            orderDetails.clear();
            updateTotalCost();
            
            // Add the drink panel to the content pane
            contentPanel.add(drinkPanel);
        }
    }



    /**
    * Updates the total cost of the order by summing up the prices of all drinks in the order.
    */
    
    /**
     * Helper function: 
     * Calculate total cost of all customer's drinks. They list of
     * customer drink is stored in the member variable listOrderingDrink
     * from DataBaseHandler class
    */
    public void updateTotalCost() {
        // Calculate the updated total cost
        totalCost = 0.0;
        for (drinkDetailDatabase drinkDetails : DatabaseHandler.listOrderingDrink) {
            totalCost += drinkDetails.totalPrice_;
        }
    }

    /**
     * Summarize the Topping and Ingredient is used before placing order
     * This function is helper function to the Check out Handler to make
     * sure that the ingredient and topping availability is updated 
     * correctly following the drinks that were ordered.
     * @param drinkDetail drinkDetailDatabase object
    */
    public void updateToppingsAndIngredients(drinkDetailDatabase drinkDetail) {
        for (HashMap<String, Integer> toppingList : drinkDetail.toppingList_) {
            String toppingName = toppingList.keySet().toArray()[0].toString();
            Integer toppingCount = toppingList.get(toppingName);
            // Find the topping ID for the removed topping
            Integer toppingID = DatabaseHandler.toppingIdMap.get(toppingName);
            if (toppingID != null) {
                // Update the toppingUsed quantity
                DatabaseHandler.toppingUsed.put(toppingID, DatabaseHandler.toppingUsed.get(toppingID) + toppingCount);
            }
        }
    
        // Iterate over the ingredients used in the drink and update their quantities
        List<Integer> ingredientIDs = DatabaseHandler.drinkIngredientMap.get(drinkDetail.drinkID_);
        if (ingredientIDs != null) {
            for (Integer ingredientID : ingredientIDs) {
                // Update the ingredientUsed quantity
                DatabaseHandler.ingredientUsed.put(ingredientID, DatabaseHandler.ingredientUsed.get(ingredientID) + 1);
            }
        }
    }
}