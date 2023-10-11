import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class checkoutHandler {
    private static JFrame checkoutFrame;
    private static String customer_id = null;

    public static void checkoutFrame_() {
        SwingUtilities.invokeLater(() -> {
            checkoutFrame = new JFrame();
            checkoutFrame.setTitle("Drink Customization Panel");
            checkoutFrame.setSize(GUI.screenSize.width, GUI.screenSize.height);
            checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            checkoutFrame.setLayout(new BorderLayout());

            // Create a panel with drinks for the selected category
            checkoutFrame.add(GUI.checkoutPanel, BorderLayout.LINE_START);

            JButton enterCustomerIDButton = new JButton("Enter Customer ID");
            enterCustomerIDButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Prompt the user to enter a customer ID using a JOptionPane
                    customer_id = JOptionPane.showInputDialog(checkoutFrame, "Enter Customer ID:");
                    
                    // You can validate the customer_id_temp input here if needed
                }
            });

            checkoutFrame.add(enterCustomerIDButton, BorderLayout.NORTH);

            JButton insertCardButton = new JButton("Insert or Tab your Card");
            insertCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the category handler and pass the selected category.
                // UPDATE Topping Availability
                if (customer_id == null){
                    customer_id = JOptionPane.showInputDialog(checkoutFrame, "Enter Customer ID:");
                }
                for (Integer toppingID : GUI.toppingUsed.keySet()){
                    // Call the category handler and pass the selected category.
                    if (GUI.toppingUsed.get(toppingID) != 0) {
                        try {
                            boolean ranSuccessfully = GUI.run_SQL_Command("topping", 
                            """
                            UPDATE topping SET availability = availability - %d
                            WHERE topping_id = '%d';
                            """.formatted(GUI.toppingUsed.get(toppingID), toppingID));
                        } catch (NumberFormatException e_2) {
                            System.err.println("ERROR");
                        }
                    }
                    // System.out.println(toppingID + ": " + GUI.toppingUsed.get(toppingID));
                }
                
                // UPDATE Ingredient Availability
                for (Integer ingredientID : GUI.ingredientUsed.keySet()){
                    // Call the category handler and pass the selected category.
                    if (GUI.ingredientUsed.get(ingredientID) != 0) {
                        try {
                            boolean ranSuccessfully = GUI.run_SQL_Command("ingredients", 
                            """
                            UPDATE ingredients SET availability = availability - %d
                            WHERE ingredients_id = '%d';
                            """.formatted(GUI.ingredientUsed.get(ingredientID), ingredientID));
                        } catch (NumberFormatException e_2) {
                            System.err.println("ERROR");
                        }
                    }
                    // System.out.println(ingredientID + ": " + GUI.ingredientUsed.get(ingredientID));
                }
                
                // INSERT Order
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedDate = localDate.format(dateFormatter);
                String formattedTime = localTime.format(timeFormatter);
                try {
                    boolean ranSuccessfully = GUI.run_SQL_Command("orders", 
                    """
                    INSERT INTO orders (order_id, customer_id, employee_id, date, total_price, time)
                    VALUES ('%d', '%s', '%s', '%s', '%f', '%s');
                    """.formatted(GUI.nextOrderID, customer_id, GUI.employee_id, formattedDate, GUI.totalPrice, formattedTime));
                } catch (NumberFormatException e_2) {
                    System.err.println("ERROR");
                }
                

                // Debug and Reset
                System.out.println(GUI.nextOrderID);
                System.out.println(customer_id);
                System.out.println(GUI.employee_id);
                System.out.println(GUI.ingredientUsed);
                System.out.println(GUI.toppingUsed);
                GUI.checkoutPanel.removeAll();
                GUI.checkoutPanel.add(new JLabel("List of drinks"), BorderLayout.LINE_START);
                for (Integer ingredientID : GUI.ingredientUsed.keySet()){
                    GUI.ingredientUsed.put(ingredientID, 0);
                }
                for (Integer toppingID : GUI.toppingUsed.keySet()){
                    GUI.toppingUsed.put(toppingID, 0);
                }
                checkoutFrame.dispose();
            }
            });
            checkoutFrame.add(insertCardButton, BorderLayout.EAST);
            checkoutFrame.add(new JLabel("Total Price: " + GUI.totalPrice));
            checkoutFrame.setVisible(true);
        });
    }
}