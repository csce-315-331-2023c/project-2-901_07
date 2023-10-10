import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;


class drinkHandler {
    static int screenWidth = (Toolkit.getDefaultToolkit().getScreenSize()).width;
    static int screenHeight = (Toolkit.getDefaultToolkit().getScreenSize()).height;
    
    private static JLabel sugarLevelLabel;
    private static JLabel iceLevelLabel;
    private static JLabel drinkDetaiLabel;

    private static JLabel checkoutLabel;
    private static ArrayList<String> checkoutList;

    public static JPanel drinkDetailPanel;
    public static Double drinkTotalPrice = 0.0;
    private static JFrame customFrame;

    public static JPanel DrinkHandlerPanel(String drink, Double price) {
        drinkTotalPrice = price;
        checkoutList = new ArrayList<String>();
        // Create a JDialog for the popup panel
        customFrame = new JFrame();
        
        customFrame.setTitle("Drink Customization Panel");
        customFrame.setSize(screenWidth, screenHeight);
        customFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        customFrame.setLayout(new BorderLayout());
        
        customFrame.add(iceAndSugar_(),BorderLayout.NORTH);
        customFrame.add(toppingPanel_(), BorderLayout.CENTER);
        customFrame.add(drinkDetail_(drink, price), BorderLayout.EAST);

        customFrame.setVisible(true);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // You can perform any further actions with the result here
                JOptionPane.showMessageDialog(null, "An order has been added - Total Price: $" + drinkTotalPrice);
                checkoutList.add(drink + " ($" + drinkTotalPrice + ")");
                checkoutLabel = new JLabel(String.join("\n", checkoutList));
                GUI.totalPrice += drinkTotalPrice;
                GUI.checkoutPanel.add(checkoutLabel);
                customFrame.dispose();
            }
        });
        customFrame.add(submitButton, BorderLayout.WEST);

        return drinkDetailPanel;
    }

    // public static JPanel. 

    // Create a JPanel for the content of the popup
    public static JPanel iceAndSugar_() {
        JPanel iceAndSugar = new JPanel(new GridLayout(2, 0, 0, 0));
        iceAndSugar.add(icePanel_());
        iceAndSugar.add(sugarLevel_());
        return iceAndSugar;
    }

    // Section for ice level
    public static JPanel icePanel_(){
        JLabel iceLabel = new JLabel("Ice Level:");
        JPanel icePanel = new JPanel(new GridLayout(1, 0, 20, 20));
        icePanel.setPreferredSize(new Dimension(screenWidth/10, screenHeight/10));
        icePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        icePanel.add(iceLabel);
        String[] iceOptions = {"0%", "25%", "50%", "100%"};
        iceLevelLabel = new JLabel("");
        for (String option : iceOptions) {
            JButton iceButton = new JButton(option);
            icePanel.add(iceButton);
            iceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Later
                    iceLevelLabel.setText("Ice Level: " + option);
                }
            });
        }
        return icePanel;
    }

    // Section for sugar level
    public static JPanel sugarLevel_() {
        JLabel sugarLabel = new JLabel("Sugar Level:");
        JPanel sugarPanel = new JPanel(new GridLayout(1, 0, 20, 20));
        sugarPanel.setPreferredSize(new Dimension(screenWidth / 10, screenHeight / 10));
        sugarPanel.add(sugarLabel);
        sugarPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        String[] sugarOptions = {"0%", "25%", "50%", "100%"};
        sugarLevelLabel = new JLabel(""); // Initialize the sugarLevelLabel
        for (String option : sugarOptions) {
            JButton sugarButton = new JButton(option);
            sugarPanel.add(sugarButton);
            sugarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Update the sugar level label text when a button is clicked
                    sugarLevelLabel.setText("Sugar Level: " + option);
                }
            });
        }
        // Add the sugarLevelLabel to the drinkDetailPanel
        return sugarPanel;
    }

    // Section for toppings
    public static JScrollPane toppingPanel_() {
        JLabel toppingLabel = new JLabel("Toppings:");
        JPanel toppingPanel = new JPanel(new GridLayout(10, 2, 20, 20));
        toppingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        toppingPanel.add(toppingLabel);

        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("price");
        columnNames.add("topping_id");
        List<List<String>> toppings = GUI.query("topping", columnNames);

        for (List<String> topping : toppings) {
            String toppingName = topping.get(0);
            Double price = Double.parseDouble(topping.get(1));
            Integer toppingID = Integer.parseInt(topping.get(2));
            GUI.toppingIdMap.put(toppingName, toppingID);

            // Create a button with the topping name, price, and topping types
            JButton toppingButton = new JButton(toppingName + " ($" + price + ")");
            toppingPanel.add(toppingButton);

            // Add an ActionListener to handle button click
            toppingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String toppingName = topping.get(0);
                    Double price = Double.parseDouble(topping.get(1));
                    // Check if the topping is already in the drinkDetailPanel
                    boolean isToppingSelected = false;
                    Component[] components = drinkDetailPanel.getComponents();
                    for (Component component : components) {
                        if (component instanceof JLabel) {
                            JLabel label = (JLabel) component;
                            if (label.getText().startsWith(toppingName)) {
                                // Topping is already selected, so remove it
                                drinkDetailPanel.remove(component);
                                drinkTotalPrice -= price;
                                isToppingSelected = true;
                                break;
                            }
                        }
                    }
                    // If the topping is not already selected, add it to the drinkDetailPanel
                    if (!isToppingSelected) {
                        JLabel toppingLabel = new JLabel(toppingName + " ($" + price + ")");
                        drinkDetailPanel.add(toppingLabel);
                        drinkTotalPrice += price;
                    }

                    // Repaint the panel to reflect the changes
                    drinkDetailPanel.revalidate();
                    drinkDetailPanel.repaint();
                }
            });
        }

        JScrollPane scrollPane = new JScrollPane(toppingPanel);
        scrollPane.setPreferredSize(new Dimension(screenWidth / 12, screenHeight / 2));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    public static JPanel drinkDetail_(String drink, Double drinkPrice){
        drinkDetaiLabel = new JLabel("Drink Details:" + " ($" + drinkPrice + ")");
        drinkDetailPanel = new JPanel(new GridLayout(10, 2, 20, 20));
        drinkDetailPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        drinkDetailPanel.setPreferredSize(new Dimension(screenWidth/5, screenHeight/2));
        drinkDetailPanel.add(drinkDetaiLabel);
        drinkDetailPanel.add(iceLevelLabel);
        drinkDetailPanel.add(sugarLevelLabel);

        return drinkDetailPanel;
    }    
}

