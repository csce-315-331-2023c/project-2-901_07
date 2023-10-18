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
* update toppings and ingredients, and finalize their order.* @author Quy Van
* @see checkoutHandler
*/

public class checkoutHandler {
    private JFrame checkoutFrame;
    private String customerName = null;
    private Integer customerID;
    private double totalCost;
    private JPanel contentPanel;

    /**
     * Create a Frame to contain all the Panel created by populateDrinkOrder
     * and a button to submit the final order. If the button is clicked, the order
     * will be placed. Moreover, the database of topping, ingredient availability,
     * and order will be updated.
     * @see checkoutFrame_
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
                if (!DatabaseHandler.customerNameIdMap.containsKey(customerName)){
                    customerID = DatabaseHandler.customerNameIdMap.size();
                    try {
                        boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("orders", 
                        """
                        INSERT INTO customer (customer_id, name)
                        VALUES ('%d', '%s');
                        """.formatted(customerID, customerName));
                    } catch (NumberFormatException e_2) {
                        System.err.println("ERROR");
                    }
                }
                else {
                    customerID = DatabaseHandler.customerNameIdMap.get(customerName);
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
                    boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("orders", 
                    """
                    INSERT INTO orders (order_id, customer_id, employee_id, date, total_price, time)
                    VALUES ('%d', '%s', '%s', '%s', '%f', '%s');
                    """.formatted(DatabaseHandler.orderData.size(), customerID, GUI.employee_id, formattedDate, totalCost, formattedTime));
                } catch (NumberFormatException e_2) {
                    System.err.println("ERROR");
                }
                
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
     * @see populateDrinkOrder
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
     * @see updateTotalCost
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
     * @see updateToppingsAndIngredients
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