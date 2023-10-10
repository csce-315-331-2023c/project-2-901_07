import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class drinkHandler {
    static int screenWidth = (Toolkit.getDefaultToolkit().getScreenSize()).width;
    static int screenHeight = (Toolkit.getDefaultToolkit().getScreenSize()).height;

    public static void DrinkHandlerPanel(String drink, Double drinkPrice) {
        // Create a JDialog for the popup panel
        JFrame customFrame = new JFrame();
        
        customFrame.setTitle("Drink Customization Panel");
        customFrame.setSize(screenWidth, screenHeight);
        customFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        customFrame.setLayout(new BorderLayout());
        
        customFrame.add(iceAndSugar_(),BorderLayout.NORTH);
        customFrame.add(topppingPanel_(), BorderLayout.CENTER);
        customFrame.add(drinkDetail_(drink, drinkPrice), BorderLayout.EAST);

        customFrame.setVisible(true);
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
        for (String option : iceOptions) {
            JButton iceButton = new JButton(option);
            icePanel.add(iceButton);
        }
        return icePanel;
    }

    // Section for sugar level
    public static JPanel sugarLevel_(){
        JLabel sugarLabel = new JLabel("Sugar Level:");
        JPanel sugarPanel = new JPanel(new GridLayout(1, 0, 20, 20));
        sugarPanel.setPreferredSize(new Dimension(screenWidth/10, screenHeight/10));
        sugarPanel.add(sugarLabel);
        sugarPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        String[] sugarOptions = {"0%", "25%", "50%", "100%"};
        for (String option : sugarOptions) {
            JButton sugarButton = new JButton(option);
            sugarPanel.add(sugarButton);
        }
        return sugarPanel;
    }

    // Section for toppings
    public static JScrollPane topppingPanel_(){
        JLabel toppingLabel = new JLabel("Toppings:");
        JPanel toppingPanel = new JPanel(new GridLayout(10, 2, 20, 20));
        toppingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        toppingPanel.add(toppingLabel);
        
        HashMap<String, Object[]> toppings = new HashMap<>();
        toppings.put("Pearls", new Object[]{0.75, new String[]{"Tapioca", "Brown Sugar"}});
        toppings.put("Mini Pearls", new Object[]{0.75, new String[]{"Mini Tapioca", "Brown Sugar"}});
        toppings.put("Ice Cream", new Object[]{1.00, new String[]{"Ice Cream"}});
        toppings.put("Pudding", new Object[]{0.75, new String[]{"Pudding"}});
        toppings.put("Aloe Vera", new Object[]{0.75, new String[]{"Aloe Vera"}});
        toppings.put("Red Bean", new Object[]{0.75, new String[]{"Red Bean"}});
        toppings.put("Herb Jelly", new Object[]{0.75, new String[]{"Herb Jelly"}});
        toppings.put("Aiyu Jelly", new Object[]{0.75, new String[]{"Aiyu Jelly"}});
        toppings.put("Lychee Jelly", new Object[]{0.75, new String[]{"Lychee Jelly"}});
        toppings.put("Creama", new Object[]{1.00, new String[]{"Creama"}});

        for (Map.Entry<String, Object[]> entry : toppings.entrySet()) {
            String toppingName = entry.getKey();
            Object[] toppingData = entry.getValue();
            double price = (double) toppingData[0];
            String[] ingredient = (String[]) toppingData[1];

            // Create a button with the topping name, price, and topping types
            JButton toppingButton = new JButton(toppingName + " ($" + price + ") - " + String.join(", ", ingredient));
            toppingPanel.add(toppingButton);

            // Add an ActionListener to handle button click
            toppingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Later
                }
            });
        }
        JScrollPane scrollPane = new JScrollPane(toppingPanel);
        scrollPane.setPreferredSize(new Dimension(screenWidth/12, screenHeight/2));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    public static JPanel drinkDetail_(String drink, Double drinkPrice){
        JLabel drinkDetaiLabel = new JLabel("Drink Details:");
        JPanel drinkDetailPanel = new JPanel(new GridLayout(10, 2, 20, 20));
        drinkDetailPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        drinkDetailPanel.setPreferredSize(new Dimension(screenWidth/5, screenHeight/2));
        drinkDetailPanel.add(drinkDetaiLabel);
        return drinkDetailPanel;
    }    
}

