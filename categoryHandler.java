import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
/*
 * @author Quy Van
 */
public class categoryHandler {
    
    private static int screenWidth = (Toolkit.getDefaultToolkit().getScreenSize()).width;
    private static int screenHeight = (Toolkit.getDefaultToolkit().getScreenSize()).height;
    private static JFrame customFrame;
    private static JPanel drinkDetail;
    /* @param category the category of drink
    *@return returns a panel with the list of drinks

    */
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
    /*
     * @param category the category of drink
        *@return returns a panel with the list of drinks
     */
    public static JPanel createDrinksPanel(String category) {
        // Assuming you have a data structure containing drinks for each category.
        // You can modify the logic to retrieve drinks based on the selected category.
        HashMap<String, Double> drinkMap = GUI.drinkPrices.get(category);
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
}
