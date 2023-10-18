import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

/** 
* Create Frame to include Button of Each Drink of specified Drink Type.
* @author Quy Van
*/

public class drinkHandler {
    private drinkDetailDatabase thisDrink = new drinkDetailDatabase();
    private static JLabel sugarLevelLabel;
    private static JLabel iceLevelLabel;
    private static JLabel drinkDetaiLabel;
    private static JPanel drinkDetailPanel;
    private static JFrame customFrame;

    /** 
    * Create Frame to include Button sugar level, ice level, and topping list
    * When the button is clicked, the information is added to temporary HashMap
    * @param drink name of drink
    * @param price price of the specified drink
    */
    public void DrinkHandlerPanel(String drink, Double price) {
        // Set up data
        thisDrink.drinkPrice_ = price;
        thisDrink.drinkName_ = drink;
        thisDrink.drinkID_ = DatabaseHandler.drinkNameIdMap.get(thisDrink.drinkName_);

        // Create a JFrame for the popup panel
        customFrame = new JFrame();
        
        customFrame.setTitle("Drink Customization Panel");
        customFrame.setSize(GUI.screenSize.width, GUI.screenSize.height);
        customFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        customFrame.setLayout(new BorderLayout());
        
        customFrame.add(iceAndSugar_(),BorderLayout.NORTH);
        customFrame.add(toppingPanel_(), BorderLayout.CENTER);
        customFrame.add(drinkDetail_(drink, price), BorderLayout.EAST);

        customFrame.setVisible(true);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculate the total price including drink price and topping prices
                thisDrink.totalPrice_  = thisDrink.drinkPrice_;

                for (HashMap<String, Integer> toppingCountMap : thisDrink.toppingList_) {
                    String toppingName = toppingCountMap.keySet().toArray()[0].toString();
                    int toppingCount = toppingCountMap.get(toppingName);
                    // Find the price of the selected topping
                    Double toppingPrice = DatabaseHandler.toppingPriceMap.get(toppingName);
                    thisDrink.totalPrice_  += toppingPrice * toppingCount;
                }
                JOptionPane.showMessageDialog(null, "An order has been added - Total Price: $" + thisDrink.totalPrice_);
                DatabaseHandler.listOrderingDrink.add(thisDrink);
                customFrame.dispose();
            }
        });
        customFrame.add(submitButton, BorderLayout.WEST);
    }

    /** 
    * Create a Panel containing ice level and sugar level
    * @return Returns Panel containing ice level and sugar level
    */  
    public JPanel iceAndSugar_() {
        JPanel iceAndSugar = new JPanel(new GridLayout(2, 0, 0, 0));
        iceAndSugar.add(icePanel_());
        iceAndSugar.add(sugarLevel_());
        return iceAndSugar;
    }

    /** 
    * Create a Panel containing all buttons for each ice level option
    * @return Returns Panel containing all buttons for each ice level option
    */    
    public JPanel icePanel_(){
        JLabel iceLabel = new JLabel("Ice Level:");
        JPanel icePanel = new JPanel(new GridLayout(1, 0, 20, 20));
        icePanel.setPreferredSize(new Dimension(GUI.screenSize.width/10, GUI.screenSize.height/10));
        icePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        icePanel.add(iceLabel);
        String[] iceOptions = {"No Ice", "Less Ice", "Normal Ice"};
        iceLevelLabel = new JLabel("");
        for (String option : iceOptions) {
            JButton iceButton = new JButton(option);
            icePanel.add(iceButton);
            iceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Later
                    iceLevelLabel.setText("Ice Level: " + option +"%");
                    thisDrink.iceLevel_ = option;
                }
            });
        }
        return icePanel;
    }

    /** 
    * Create a Panel containing all buttons for each sugar level option
    * @return Returns Panel containing all buttons for each sugar level option
    */
    public JPanel sugarLevel_() {
        JLabel sugarLabel = new JLabel("Sugar Level:");
        JPanel sugarPanel = new JPanel(new GridLayout(1, 0, 20, 20));
        sugarPanel.setPreferredSize(new Dimension(GUI.screenSize.width / 10, GUI.screenSize.height / 10));
        sugarPanel.add(sugarLabel);
        sugarPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        String[] sugarOptions = {"0", "30", "50", "80", "100"};
        sugarLevelLabel = new JLabel(""); // Initialize the sugarLevelLabel
        for (String option : sugarOptions) {
            JButton sugarButton = new JButton(option);
            sugarPanel.add(sugarButton);
            sugarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Update the sugar level label text when a button is clicked
                    sugarLevelLabel.setText("Sugar Level: " + option);
                    thisDrink.sugarLevel_ = option;
                }
            });
        }
        // Add the sugarLevelLabel to the drinkDetailPanel
        return sugarPanel;
    }

    /** 
    * This function pull list of topping from database and display
    * them in 1 scroll panel. 
    * @return Return Scroll Panel for list of toppings
    */
    public JScrollPane toppingPanel_() {
        JLabel toppingLabel = new JLabel("Toppings:");
        JPanel toppingPanel = new JPanel(new GridLayout(10, 2, 20, 20));
        toppingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        toppingPanel.add(toppingLabel);
    
        for (List<String> topping : DatabaseHandler.toppingData) {
            String toppingName = topping.get(1);
            Double price = Double.parseDouble(topping.get(2));
    
            // Create a button with the topping name, price, and topping types
            JButton toppingButton = new JButton(toppingName + " ($" + price + ")");
            toppingPanel.add(toppingButton);
    
            // Add an ActionListener to handle button click
            toppingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Check if the topping is already in the toppingList_
                    boolean isToppingSelected = false;
                    for (HashMap<String, Integer> toppingMap : thisDrink.toppingList_) {
                        if (toppingMap.containsKey(toppingName)) {
                            // Topping is already selected, so increment the count
                            int currentCount = toppingMap.get(toppingName);
                            toppingMap.put(toppingName, currentCount + 1);
                            isToppingSelected = true;
                            break;
                        }
                    }

                    // If the topping is not already selected, add it to the toppingList_
                    if (!isToppingSelected) {
                        HashMap<String, Integer> newTopping = new HashMap<>();
                        newTopping.put(toppingName, 1);
                        thisDrink.toppingList_.add(newTopping);
                    }

                    // Update the drinkDetailPanel to display the selected toppings
                    updateDrinkDetailPanel();
                }
            });
        }
    
        JScrollPane scrollPane = new JScrollPane(toppingPanel);
        scrollPane.setPreferredSize(new Dimension(GUI.screenSize.width / 12, GUI.screenSize.height / 2));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }
    
    /** 
    * This function add Label of drink information to 1 Panel then return
    * the Panel to be added to Frame.
    * @param drink name of drink
    * @param drinkPrice price of the specified drink
    * @return return Panel containing all drink detail for this drink 
    */
    public JPanel drinkDetail_(String drink, Double drinkPrice){
        drinkDetaiLabel = new JLabel("Drink Details:" + drink + " ($" + drinkPrice + ")");
        drinkDetailPanel = new JPanel(new GridLayout(10, 2, 20, 20));
        drinkDetailPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        drinkDetailPanel.setPreferredSize(new Dimension(GUI.screenSize.width/5, GUI.screenSize.height/2));
        drinkDetailPanel.add(drinkDetaiLabel);
        drinkDetailPanel.add(iceLevelLabel);
        drinkDetailPanel.add(sugarLevelLabel);

        JLabel toppingsLabel = new JLabel("Selected Toppings:");
        drinkDetailPanel.add(toppingsLabel);

        JLabel totalLabel = new JLabel("Total Price: $" + thisDrink.drinkPrice_);
        drinkDetailPanel.add(totalLabel);

        return drinkDetailPanel;
    }    

    /** 
    * Function to update the Panel of drink information if sugar level,
    * ice level, or topping is changed during customization process
    */
    public void updateDrinkDetailPanel() {
        drinkDetailPanel.removeAll(); // Clear the existing components
    
        // Add the drink details
        drinkDetailPanel.add(drinkDetaiLabel);
        drinkDetailPanel.add(iceLevelLabel);
        drinkDetailPanel.add(sugarLevelLabel);
    
        // Display selected toppings and their quantities
        JLabel toppingsLabel = new JLabel("Selected Toppings:");
        drinkDetailPanel.add(toppingsLabel);
    
        for (HashMap<String, Integer> toppingMap : thisDrink.toppingList_) {
            for (Map.Entry<String, Integer> entry : toppingMap.entrySet()) {
                String toppingName = entry.getKey();
                int toppingCount = entry.getValue();
    
                // Create a remove button for each topping
                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Remove the topping when the remove button is clicked
                        removeTopping(toppingName);
                    }
                });
    
                JLabel toppingLabel = new JLabel(toppingName + ": ($" + DatabaseHandler.toppingPriceMap.get(toppingName) +") x " + toppingCount);
                drinkDetailPanel.add(toppingLabel);
                drinkDetailPanel.add(removeButton);
            }
        }
    
        drinkDetailPanel.revalidate();
        drinkDetailPanel.repaint();
    }
    
    /** 
    * Update topping count from toppingList_ variable of drinkDetailDatabase object
    * @param toppingName name of topping to remove from topping list
    */
    public void removeTopping(String toppingName) {
        // Iterate through the toppingList_ and find the matching topping to remove
        for (HashMap<String, Integer> toppingMap : thisDrink.toppingList_) {
            if (toppingMap.containsKey(toppingName)) {
                int toppingCount = toppingMap.get(toppingName);
                if (toppingCount > 1) {
                    // If there are more than one of the same topping, decrement the count
                    toppingMap.put(toppingName, toppingCount - 1);
                } else {
                    // If there's only one of the topping, remove it from the list
                    thisDrink.toppingList_.remove(toppingMap);
                }
                // Update the drinkDetailPanel to reflect the removal
                updateDrinkDetailPanel();
                return; // Exit the loop since the topping has been found and processed
            }
        }
    }
}
