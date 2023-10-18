import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/** 
* Create Frame to include Button of Each Drink of specified Drink Type.
* @author Quy Van
*/
public class categoryHandler {
    
    private static int screenWidth = (Toolkit.getDefaultToolkit().getScreenSize()).width;
    private static int screenHeight = (Toolkit.getDefaultToolkit().getScreenSize()).height;
    private static JFrame customFrame;
    private static JPanel drinkDetail;
 
     /**
     * Set up Frame Configuration to contain Panels of drinks. 
     * @param category the category of drink
    */
    public static void categoryHandlerPanel(String category) {
        customFrame = new JFrame();
        customFrame.setTitle("Drink Customization Panel");
        customFrame.setSize(screenWidth, screenHeight);
        customFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        customFrame.setLayout(new BorderLayout());

        // Create a panel with drinks for the selected category
        JPanel drinksPanel = createDrinksPanel(category);
        customFrame.add(drinksPanel, BorderLayout.CENTER);

        customFrame.setVisible(true);
    }
    
    /**
     * Utilize Database Handler to pull list of drink in the specified category
     * and create Button for each drink. When the Button is clicked, the Drink Handler
     * is called to determine sugar level, ice level, and toppings.
     * @param category the Drink Type that was specified when the function is called
     * @return returns a panel with the list of drinks
     */
    public static JPanel createDrinksPanel(String category) {
        List<String> drinkList = DatabaseHandler.drinkTypeMap.get(category);
        
        JLabel label = new JLabel("List of " + category);
        
        JPanel panel = new JPanel(new GridLayout(5, 5, 20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(label, BorderLayout.NORTH);

        for (String drink : drinkList) {
            Double price = DatabaseHandler.drinkPriceMap.get(drink);
            JButton drinkButton = new JButton(drink + " ($" + price + ")");

            drinkButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Call the category handler and pass the selected category.
                    drinkHandler drinkDetail = new drinkHandler();
                    drinkDetail.DrinkHandlerPanel(drink, price);
                    customFrame.dispose();
                }
            });
            panel.add(drinkButton, BorderLayout.SOUTH);
        }
        return panel;
    }
}
