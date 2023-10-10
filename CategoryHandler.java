import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class categoryHandler {
    
    private static int screenWidth = (Toolkit.getDefaultToolkit().getScreenSize()).width;
    private static int screenHeight = (Toolkit.getDefaultToolkit().getScreenSize()).height;
    private static JFrame customFrame;
    private static JPanel drinkDetail;

    public static JPanel categoryHandlerPanel(String category) {
        SwingUtilities.invokeLater(() -> {
            customFrame = new JFrame();
            customFrame.setTitle("Drink Customization Panel");
            customFrame.setSize(screenWidth, screenHeight);
            customFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            customFrame.setLayout(new BorderLayout());

            // Create a panel with drinks for the selected category
            JPanel drinksPanel = createDrinksPanel(category);
            customFrame.add(drinksPanel, BorderLayout.CENTER);

            customFrame.setVisible(true);
        });
        return drinkDetail;
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
                    drinkDetail = drinkHandler.DrinkHandlerPanel(drink, price);
                    customFrame.dispose();
                }
            });
            panel.add(drinkButton, BorderLayout.SOUTH);
        }
        return panel;
    }

    public static HashMap<String, Double> getDrinkDict(String category){
        HashMap<String, HashMap<String, Double>> drinkPrices = new HashMap<>();
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
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
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

        return drinkPrices.get(category);
    }
}
