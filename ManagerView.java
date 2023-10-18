import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.naming.spi.DirStateFactory.Result;
import java.util.Set;
import javax.swing.border.*;
import javax.tools.JavaFileManager;
import java.awt.event.*;

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
    static public JPanel lowStockPage(){
        int count = 0;
        //Method contains commands to format inventory page
        lowStockPage = new JPanel(new GridLayout(0, 5, 5, 5));
        lowStockPage.setBorder(new EmptyBorder(20, 40, 200, 40));
        JLabel pageTitle = new JLabel("");
        pageTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        lowStockPage.add(pageTitle);
        lowStockPage.add(new JLabel());
        lowStockPage.add(new JLabel());
        lowStockPage.add(new JLabel());
        lowStockPage.add(new JLabel());

        lowStockPage.setBackground(Color.white);
        lowStockPage.setAutoscrolls(true);

        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("price");
        columnNames.add("availability");

        

        for (List<String> topping : DatabaseHandler.toppingData) {
            if(Integer.parseInt(topping.get(3)) <= lowStockNumber){
                count++;
                JButton toppingButton = new JButton( "<html>%s<br>Price: $%s<br>Stock: %s</html>".formatted(topping.get(1),topping.get(2),topping.get(3)));
                toppingButton.setPreferredSize(new Dimension(100, 40));
                toppingButton.setBackground(Color.orange);
                    toppingButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            frameOpened.dispose();
                            ToppingInventoryFrame tempToppingInventoryFrame = new ToppingInventoryFrame(toppingButton, topping);
                            frameOpened = tempToppingInventoryFrame.frame;

                        }
                    });
                lowStockPage.add(toppingButton);
            }
          
        }

        columnNames.clear();
        columnNames.add("name");
        columnNames.add("availability");

        for (List<String> ingredient : DatabaseHandler.ingredientData) {
            if(Integer.parseInt(ingredient.get(2)) <= lowStockNumber){
                count++;
                JButton ingredientsButton = new JButton( "<html>%s<br>Stock:%s</html>".formatted(ingredient.get(1),ingredient.get(2)));
                ingredientsButton.setPreferredSize(new Dimension(100, 40));
                ingredientsButton.setBackground(Color.orange);
                    ingredientsButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            frameOpened.dispose();
                            IngredientInventoryFrame tempIngredientInventoryFrame = new IngredientInventoryFrame(ingredientsButton, ingredient);
                            frameOpened = tempIngredientInventoryFrame.frame;
                        }
                    });
                lowStockPage.add(ingredientsButton);
            }
        }
        pageTitle.setText(count + " items are low in stock");
        return lowStockPage;
        
    }

    /**
     * Constructor for the ManagerView. Sets up the database connection and initial view configurations.
     */
     ManagerView(){

     //Building the connection with your credentials
     }
}

