import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class categoryHandler {
    
    private static int screenWidth = (Toolkit.getDefaultToolkit().getScreenSize()).width;
    private static int screenHeight = (Toolkit.getDefaultToolkit().getScreenSize()).height;
    
    public static void categoryHandlerPanel(String category) {
        SwingUtilities.invokeLater(() -> {
            JFrame customFrame = new JFrame();
            customFrame.setTitle("Drink Customization Panel");
            customFrame.setSize(screenWidth, screenHeight);
            customFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            customFrame.setLayout(new BorderLayout());

            // Create a panel with drinks for the selected category
            JPanel drinksPanel = createDrinksPanel(category);
            customFrame.add(drinksPanel, BorderLayout.CENTER);

            customFrame.setVisible(true);
        });
    }

    public static JPanel createDrinksPanel(String category) {
        // Assuming you have a data structure containing drinks for each category.
        // You can modify the logic to retrieve drinks based on the selected category.
        HashMap<String, Double> drinkMap = getDrinkDict(category);
        String[] drinkList = drinkMap.keySet().toArray(new String[0]);
        
        JLabel label = new JLabel("List of " + category);
        
        JPanel panel = new JPanel(new GridLayout(5, 5, 20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(label, BorderLayout.NORTH);

        for (String drink : drinkList) {
            Double price = drinkMap.get(drink);
            JButton drinkButton = new JButton(drink + " ($" + price + ")");

            drinkButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Call the category handler and pass the selected category.
                    drinkHandler.DrinkHandlerPanel(drink, price);
                }
            });
            panel.add(drinkButton, BorderLayout.SOUTH);
        }
        return panel;
    }

    public static HashMap<String, Double> getDrinkDict(String category){
        HashMap<String, HashMap<String, Double>> drinkPrices = new HashMap<>();

        HashMap<String, Double> milkTea = new HashMap<>();
        milkTea.put("Classic Milk Tea", 4.99);
        milkTea.put("Okinawa Milk Tea", 5.49);
        milkTea.put("Hokkaido Milk Tea", 5.49);
        milkTea.put("Coffee Milk Tea", 4.99);
        milkTea.put("Thai Milk Tea", 5.99);
        milkTea.put("Taro Milk Tea", 5.99);
        milkTea.put("Matcha Red Bean Milk Tea", 6.49);
        milkTea.put("Honey Milk Tea", 5.99);
        milkTea.put("Ginger Milk Tea", 5.49);
        milkTea.put("Mango Green Milk Tea", 6.49);
        drinkPrices.put("Milk Tea", milkTea);

        // Fresh Milk
        HashMap<String, Double> freshMilk = new HashMap<>();
        freshMilk.put("Fresh Milk Tea", 5.49);
        freshMilk.put("Wintermelon with Fresh Milk", 5.99);
        freshMilk.put("Cocoa Lover with Fresh Milk", 6.49);
        freshMilk.put("Fresh Milk Family", 6.99);
        freshMilk.put("Handmade Taro with Fresh Milk", 6.49);
        drinkPrices.put("Fresh Milk", freshMilk);

        // Ice Blend
        HashMap<String, Double> iceBlend = new HashMap<>();
        iceBlend.put("Oreo Ice Blended", 6.99);
        iceBlend.put("Milk Tea Ice Blended", 6.49);
        iceBlend.put("Taro Ice Blended", 6.99);
        iceBlend.put("Thai Tea Ice Blended", 7.49);
        iceBlend.put("Matcha Red Bean Ice Blended", 7.49);
        iceBlend.put("Coffee Ice Blended", 7.49);
        iceBlend.put("Mango Ice Blended", 7.49);
        iceBlend.put("Strawberry Ice Blended", 7.49);
        iceBlend.put("Peach Tea Ice Blended", 7.49);
        drinkPrices.put("Ice Blend", iceBlend);

        // Fruit Tea
        HashMap<String, Double> fruitTea = new HashMap<>();
        fruitTea.put("Mango Green Tea", 5.99);
        fruitTea.put("Wintermelon Lemonade", 4.99);
        fruitTea.put("Strawberry Tea", 4.99);
        fruitTea.put("Peach Tea", 4.99);
        fruitTea.put("Honey Lemonade", 4.99);
        fruitTea.put("Peach Kiwi Tea", 5.49);
        fruitTea.put("Kiwi Fruit Tea", 5.49);
        fruitTea.put("Mango Passion Fruit Tea", 6.49);
        fruitTea.put("Tropical Fruit Tea", 5.99);
        fruitTea.put("Hawaii Fruit Tea", 6.49);
        fruitTea.put("Passion Fruit, Orange, and Grapefruit Tea", 6.49);
        drinkPrices.put("Fruit Tea", fruitTea);

        // Mojito
        HashMap<String, Double> mojito = new HashMap<>();
        mojito.put("Lime Mojito", 4.99);
        mojito.put("Mango Mojito", 4.99);
        mojito.put("Peach Mojito", 4.99);
        mojito.put("Strawberry Mojito", 4.99);
        drinkPrices.put("Mojito", mojito);

        // Creama
        HashMap<String, Double> creama = new HashMap<>();
        creama.put("Tea", 5.49);
        creama.put("Wintermelon Creama", 5.99);
        creama.put("Coffee Creama", 5.49);
        creama.put("Cocoa Creama", 5.99);
        creama.put("Mango Creama", 6.49);
        creama.put("Matcha Creama", 6.49);
        drinkPrices.put("Creama", creama);
    
        return drinkPrices.get(category);
    }
}
