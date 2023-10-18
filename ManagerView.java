import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The ManagerView class represents the view for the manager operations.
 * It provides different GUI panels to manage inventory, menu items, order history, and items with low stock.
 * @author Quenten Hua
 */
public class ManagerView {
    public static int lowStockNumber = 33;
    // Class contains all methods associated with the pages for the Manager view
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static JFrame frameOpened = new JFrame(null, null);
    static JPanel inventoryPage = new JPanel(new FlowLayout(0));
    static JPanel lowStockPage = new JPanel(new FlowLayout(0));
    static JPanel menuItemPage = new JPanel(new FlowLayout(0));
    static JPanel orderHistoryPage;

    /**
     * Generates and returns a panel representing the order history.
     *
     * @return A JPanel representing the order history.
     */
    static public JPanel orderHistoryPage(){
        orderHistoryPage = new JPanel(new GridLayout(0, 5, 5, 5));
        orderHistoryPage.add(new JLabel("TEST"));
        return orderHistoryPage;
    }

    /**
     * Generates and returns a panel representing the menu items.
     *
     * @return A JPanel representing the menu items.
     */
    static public JPanel menuItemPage(){
        menuItemPage = new JPanel(new GridLayout(0, 5, 5, 5));
        menuItemPage.setBorder(new EmptyBorder(20, 40, 200, 40));
        JLabel pageTitle = new JLabel("Menu Items");
        pageTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        menuItemPage.add(pageTitle);
        menuItemPage.add(new JLabel());
        menuItemPage.add(new JLabel());
        menuItemPage.add(new JLabel());
        menuItemPage.add(new JLabel());

        menuItemPage.setBackground(Color.white);
        menuItemPage.setAutoscrolls(true);


        JButton addItemButton = new JButton("<html><center>+<br>Add Item</center></html>");
        addItemButton.setPreferredSize(new Dimension(170, 70));
            addItemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameOpened.dispose();
                    AddMenuItemFrame tempAddMenuItemFrame = new AddMenuItemFrame(addItemButton);
                    frameOpened = tempAddMenuItemFrame.frame;
                }
            });        
        menuItemPage.add(addItemButton);

        for (List<String> menuItem : DatabaseHandler.menuItemData) {
          JButton menuItemButton = new JButton( "<html>%s<br>Type: %s<br>Price: $%s</html>".formatted(menuItem.get(1),menuItem.get(2),menuItem.get(3)));
          menuItemButton.setPreferredSize(new Dimension(170, 70));
          menuItemButton.setBackground(Color.green);
            menuItemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameOpened.dispose();
                    MenuItemFrame tempMenuItemFrame = new MenuItemFrame(menuItemButton, menuItem);
                    frameOpened = tempMenuItemFrame.frame;

                }
            });
          menuItemPage.add(menuItemButton);
        }


        

        return menuItemPage;       
    }

    /**
     * Generates and returns a panel representing the inventory.
     *
     * @return A JPanel representing the inventory.
     */
    static public JPanel inventoryPage(){
        //Method contains commands to format inventory page
        inventoryPage = new JPanel(new GridLayout(0, 5, 5, 5));
        inventoryPage.setBorder(new EmptyBorder(20, 40, 200, 40));
        JLabel pageTitle = new JLabel("Inventory");
        pageTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        inventoryPage.add(pageTitle);
        inventoryPage.add(new JLabel());
        inventoryPage.add(new JLabel());
        inventoryPage.add(new JLabel());
        inventoryPage.add(new JLabel());

        inventoryPage.setBackground(Color.white);
        inventoryPage.setAutoscrolls(true);

        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("price");
        columnNames.add("availability");

        JButton addItemButton = new JButton("<html><center>+<br>Add Item</center></html>");
        addItemButton.setPreferredSize(new Dimension(170, 70));
            addItemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameOpened.dispose();
                    AddItemInventoryFrame tempAddItemInventoryFrame = new AddItemInventoryFrame(addItemButton);
                    frameOpened = tempAddItemInventoryFrame.frame;
                }
            });        
        inventoryPage.add(addItemButton);

        for (List<String> topping : DatabaseHandler.toppingData) {
          JButton toppingButton = new JButton( "<html>%s<br>Price: $%s<br>Stock: %s</html>".formatted(topping.get(1),topping.get(2),topping.get(3)));
          toppingButton.setPreferredSize(new Dimension(170, 70));
          toppingButton.setBackground(Color.green);
            toppingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameOpened.dispose();
                    ToppingInventoryFrame tempToppingInventoryFrame = new ToppingInventoryFrame(toppingButton, topping);
                    frameOpened = tempToppingInventoryFrame.frame;

                }
            });
          inventoryPage.add(toppingButton);
        }

        columnNames.clear();
        columnNames.add("name");
        columnNames.add("availability");

        for (List<String> ingredient : DatabaseHandler.ingredientData) {
          JButton ingredientsButton = new JButton( "<html>%s<br>Stock:%s</html>".formatted(ingredient.get(1),ingredient.get(2)));
          ingredientsButton.setPreferredSize(new Dimension(170, 70));
          ingredientsButton.setBackground(Color.yellow);
            ingredientsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameOpened.dispose();
                    IngredientInventoryFrame tempIngredientInventoryFrame = new IngredientInventoryFrame(ingredientsButton, ingredient);
                    frameOpened = tempIngredientInventoryFrame.frame;
                }
            });
          inventoryPage.add(ingredientsButton);
        }

        return inventoryPage;
        
    }

    /**
     * Generates and returns a panel showcasing items that are low in stock.
     *
     * @return A JPanel representing the low stock items.
     */
    public static JPanel lowStockPage() {
        
        int count = 0;
    
        JPanel lowStockPage = new JPanel();
        lowStockPage.setLayout(new BoxLayout(lowStockPage, BoxLayout.Y_AXIS));
        
        // Create a top-level panel for title and stock panels
        JPanel titleAndStockPanel = new JPanel();
        titleAndStockPanel.setLayout(new BoxLayout(titleAndStockPanel, BoxLayout.Y_AXIS));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        // Title label
        JLabel pageTitle = new JLabel("");
        pageTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        lowStockPage.add(pageTitle);
        for(int i = 0; i < 10; i++){
            lowStockPage.add(new JLabel());
        }

        // Create a new panel for the stock panel
        JPanel stockPanel = new JPanel();
        stockPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Set horizontal gap to 0
    
        
        for (List<String> topping : DatabaseHandler.toppingData) {
            if (Integer.parseInt(topping.get(3)) <= lowStockNumber) {
                count++;
                JButton toppingButton = new JButton("<html>%s<br>Price: $%s<br>Stock: %s</html>".formatted(topping.get(1), topping.get(2), topping.get(3)));
                toppingButton.setPreferredSize(new Dimension(170, 100));
                toppingButton.setBackground(Color.orange);
                toppingButton.addActionListener(e -> {
                    frameOpened.dispose();
                    ToppingInventoryFrame tempToppingInventoryFrame = new ToppingInventoryFrame(toppingButton, topping);
                    frameOpened = tempToppingInventoryFrame.frame;
                });
                stockPanel.add(toppingButton);
            }
        }
    
        for (List<String> ingredient : DatabaseHandler.ingredientData) {
            if (Integer.parseInt(ingredient.get(2)) <= lowStockNumber) {
                count++;
                JButton ingredientsButton = new JButton("<html>%s<br>Stock:%s</html>".formatted(ingredient.get(1), ingredient.get(2)));
                ingredientsButton.setPreferredSize(new Dimension(170, 100));
                ingredientsButton.setBackground(Color.orange);
                ingredientsButton.addActionListener(e -> {
                    frameOpened.dispose();
                    IngredientInventoryFrame tempIngredientInventoryFrame = new IngredientInventoryFrame(ingredientsButton, ingredient);
                    frameOpened = tempIngredientInventoryFrame.frame;
                });
                stockPanel.add(ingredientsButton);
            }
        }
        titlePanel.setBorder(null);
        stockPanel.setBorder(null);
        // Add the panels with buttons to the main panel
        // titleAndStockPanel.add(titlePanel);
        titleAndStockPanel.add(stockPanel);

        // Add the titleAndStockPanel to the main panel
        lowStockPage.add(titleAndStockPanel);
        if( count == 0){
            pageTitle.setText("There are no items that are low in stock");
        }else if(count == 1){
            pageTitle.setText("There is 1 item that is low in stock");
        }else{
            pageTitle.setText("There are " + count + " items low in stock");
        }
        
        return lowStockPage;
    }
    
    
    
    

    /**
     * Constructor for the ManagerView. Sets up the database connection and initial view configurations.
     */
     ManagerView(){

     //Building the connection with your credentials
     }
}

