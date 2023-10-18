import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * GUI class provides the graphical representation of the application
 * and handles various functionalities related to the application's interface.
 */
public class GUI{
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public DatabaseHandler databaseHandler =  new DatabaseHandler();
    
    CardLayout bottomPanelCardLayout, centerPanelCardLayout;
    
    JButton checkoutButton, homeButton, orderHistoryButton, menuItemButton, inventoryButton, trendsButton, lowStockButton;
    JButton changeEmployeeButton, toGoButton, addCustomerButton, totalChargeButton, ticketsButton;
    
    JLabel currentViewLabel, currentEmployeeLabel;
    List<List<String>> employeeInformation = new ArrayList<>();
    public static JPanel centerPanel, rightPanel, bottomPanel;
    JPanel homePage, inventoryPage, menuItemPage, lowStockPage;

    public static Double totalPrice = 0.0;

    public static String employee_id, employeeName;
    String current_view = "Cashier";
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // Bottom Horizontal Bar
    // Checkout + order History + Trends + Availability

    /**
     * Generates the bottom panel view of the application with functionalities 
     * like Checkout, order History, Trends, and Availability.
     * @return JPanel that contains the bottom panel view.
     */
    public JPanel bottomPanel() {
        // Bottom panel
        int panelHeight = screenSize.height / 10;
        int panelWidth = 0;
        bottomPanelCardLayout = new CardLayout();
        bottomPanel = new JPanel(bottomPanelCardLayout);
        bottomPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        //cashier view bottom panel
        JPanel cashierView = new JPanel(new GridLayout(1, 5, 30, 10));
        cashierView.setBorder(new EmptyBorder(20, 20, 20, 20));
        cashierView.add(new JLabel());
        cashierView.add(new JLabel());
        cashierView.add(new JLabel());
        cashierView.add(checkout_button());
        //manager view bottom panel
        JPanel managerView = new JPanel(new GridLayout(1, 5, 30, 10));
        managerView.setBorder(new EmptyBorder(20, 20, 20, 20));
        managerView.add(home_button());
        managerView.add(inventory_button());
        managerView.add(menuItem_button());
        managerView.add(trends_button());
        managerView.add(lowStock_button());
        managerView.add(checkout_button());
        bottomPanel.add(cashierView, "Cashier View");
        bottomPanel.add(managerView, "Manager View");
        
        return bottomPanel;
    }
    
    // 1. Checkout Button
    /**
     * Creates and returns a checkout button with its associated functionalities.
     * @return JButton for the checkout operation.
     */
    public JButton checkout_button() {
        checkoutButton = new JButton("Checkout");
        checkoutButton.setMargin(new Insets(50, 50, 50, 50));
        checkoutButton.setFont(new Font("Calibri", Font.BOLD, 16));
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalPrice = 0.0;
                // Call the category handler and pass the selected category.
                checkoutHandler checkoutFrame = new checkoutHandler();
                checkoutFrame.checkoutFrame_();
            }
        });
        return checkoutButton;
    }

    // 2. order Button
    /**
     * Creates and returns an order history button with its associated functionalities.
     * @return JButton for viewing the order history.
     */
    public JButton orderHistory_button() {
        orderHistoryButton = new JButton("Order History");
        orderHistoryButton.setFont(new Font("Calibri", Font.BOLD, 16));
        orderHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanelCardLayout.show(centerPanel, "Order History Page");
            }
        });
        return orderHistoryButton;
    }

    // 3. Trends Button
    /**
     * Creates and returns a menu item button with its associated functionalities.
     * @return JButton for viewing the menu items.
     */
    public JButton menuItem_button() {
        menuItemButton = new JButton("Menu Items");
        menuItemButton.setFont(new Font("Calibri", Font.BOLD, 16));
        menuItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanelCardLayout.show(centerPanel, "Menu Item Page");
            }
        });
        return menuItemButton;
    }

    // 4. Availability Button
    /**
     * Creates and returns an inventory button with its associated functionalities.
     * @return JButton for viewing the inventory.
     */
    public JButton inventory_button() {
        inventoryButton = new JButton("Inventory");
        inventoryButton.setFont(new Font("Calibri", Font.BOLD, 16));
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanelCardLayout.show(centerPanel, "Inventory Page");
            }
        });
        return inventoryButton;
    }

    /**
     * Creates and returns a low stock button with its associated functionalities.
     * @return JButton for viewing the items in low stock.
     */
    public JButton lowStock_button() {
        lowStockButton = new JButton("Low Stock");
        lowStockButton.setFont(new Font("Calibri", Font.BOLD, 16));
        lowStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.centerPanel.add(new JScrollPane(ManagerView.lowStockPage()), "Low Stock Page");
                centerPanelCardLayout.show(centerPanel, "Low Stock Page");
            }
        });
        return lowStockButton;
    }

    /**
     * Creates and returns a home button with its associated functionalities.
     * @return JButton for navigating to the home page.
     */
    public JButton home_button() {
        homeButton = new JButton("Home");
        homeButton.setFont(new Font("Calibri", Font.BOLD, 16));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanelCardLayout.show(centerPanel, "Home Page");
            }
        });
        return homeButton;
    }

    /**
     * Creates and returns a trends button with its associated functionalities.
     * @return JButton for viewing the trends.
     */
    public JButton trends_button(){
        trendsButton = new JButton("Trends");
        trendsButton.setFont(new Font("Calibri", Font.BOLD, 16));
        trendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanelCardLayout.show(centerPanel, "Trends Page");
            }
        });
        return trendsButton;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    // Right Vertical Bar
    // Switch View + To Go + Charge Total + Tickets
    /**
     * Generates the right panel view of the application with functionalities 
     * like Switching Views, Charging Totals, and Tickets.
     * @return JPanel that contains the right panel view.
     */
    public JPanel rightPanel() {
        employeeInformation = DatabaseHandler.employeeData;
        List<String> ids = new ArrayList<>();
        
        for (List<String> row : employeeInformation) {
            if (!row.isEmpty()) {
                ids.add(row.get(0));
            }
        }
        do{
            employee_id = JOptionPane.showInputDialog("Enter Employee ID:");
            if (employee_id == null){
                break;
            }
        }while(!ids.contains(employee_id));
        employeeName = (employeeInformation.get(Integer.parseInt(employee_id))).get(2);
        // Right panel
        int panelHeight = 0; // value does not matter
        int panelWidth = screenSize.width / 6;
        JPanel rightPanel = new JPanel(new GridLayout(0, 1, 20, 20));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        rightPanel.setBackground(new Color(144, 44, 62));
        rightPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        currentViewLabel = new JLabel("Current View: " + current_view);
        
        currentViewLabel.setFont(new Font("Calibri ", Font.BOLD, 15));
        currentViewLabel.setForeground(Color.white);
        currentViewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        currentEmployeeLabel = new JLabel("Welcome,  " + employeeName);
        currentEmployeeLabel.setFont(new Font("Calibri ", Font.BOLD, 15));
        currentEmployeeLabel.setForeground(Color.white);
        currentEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //Logo
        ImageIcon imageIcon = new ImageIcon("assets/sharetealogo.png");
        Image image = imageIcon.getImage(); // Get the original image
        int width = screenSize.width / 6; // Set the desired width
        int height = screenSize.height / 9; // Set the desired height
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Scale the image
        imageIcon = new ImageIcon(scaledImage); // Create a new ImageIcon with the scaled image
        
        JLabel label = new JLabel(imageIcon);
        
        rightPanel.add(label);
        rightPanel.add(currentEmployeeLabel);
        rightPanel.add(currentViewLabel);

        rightPanel.add(changeEmployee_button());
        return rightPanel;
        
    }

    // 1. Switch View
    /**
     * Creates and returns a button that allows users to switch the active employee.
     * @return JButton for changing the employee.
     */ 
    public JButton changeEmployee_button() {
        changeEmployeeButton = new JButton("Change Employee");
        changeEmployeeButton.setBounds(200, 100, 100, 50);
        changeEmployeeButton.setFont(new Font("Calibri", Font.BOLD, 16));
        changeEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<List<String>> employeeInformation = DatabaseHandler.employeeData;
                List<String> ids = new ArrayList<>();
                List<String> manager_ids = new ArrayList<>();
                for (List<String> row : employeeInformation) {
                    if (!row.isEmpty()) {
                        ids.add(row.get(0));
                        if(row.size() >= 2 && row.get(1).equals("t")){
                            manager_ids.add(row.get(0));
                        }
                    }
                }

                do{
                        employee_id = JOptionPane.showInputDialog("Enter Employee ID:");
                        if (employee_id == null){
                            break;
                        }
                }while(!ids.contains(employee_id));
                System.out.println(employee_id);
                Integer employee_id_index = Integer.parseInt(employee_id);
                if(employeeInformation.get(employee_id_index).get(1).equals("t")){
                    current_view = "Manager";
                    bottomPanelCardLayout.show(bottomPanel, "Manager View");
                }
                else{
                    current_view = "Cashier";
                    bottomPanelCardLayout.show(bottomPanel, "Cashier View");
                }
                centerPanelCardLayout.show(centerPanel, "Home Page");
                currentViewLabel.setText("Current View: " + current_view);
                currentEmployeeLabel.setText("Welcome,  " + employeeInformation.get(employee_id_index).get(2));
    
            }
        });
        return changeEmployeeButton;
    }

    

    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Creates and returns a JPanel for the home page which contains buttons
     * for different beverage categories.
     * 
     * @return JPanel containing the home page elements.
     */
    public JPanel homePage() {
        int panelHeight = 300;
        int panelWidth = screenSize.width - (screenSize.width / 6);
        JPanel homePagePanel = new JPanel(new GridLayout(3, 4, 30, 30));
        homePagePanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        homePagePanel.setBackground(Color.white);
        homePagePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        String[] categories = {"Milk Tea", "Fresh Milk", "Ice Blend", "Fruit Tea", "Mojito", "Creama"};

        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setBackground(Color.gray);
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Call the category handler and pass the selected category.
                    categoryHandler.categoryHandlerPanel(category);
                }
            });
            homePagePanel.add(categoryButton);
        }
        return homePagePanel;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Creates and returns a JPanel for the trends page which contains different 
     * panels navigated through buttons on a navigation bar.
     * 
     * @return JPanel containing the trends page elements.
     */
    public JPanel trendsPage() {
        int panelHeight = 300;
        int panelWidth = screenSize.width - (screenSize.width / 6);

        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        JPanel navBar = new JPanel(new GridLayout(1, 4));
        navBar.setBorder(new EmptyBorder(40, 40, 40, 40));
        // create different panels   

        // sales report     
        SalesReportPanel salesReportHandler = new SalesReportPanel();
        JPanel salesReportPanel = salesReportHandler.getSalesReportPanel();
        contentPanel.add(salesReportPanel, "Sales Report");

        JButton btnSalesReport = new JButton("Sales Report");
        btnSalesReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Sales Report");
            }
        });
        navBar.add(btnSalesReport);


        // excess report
        ExcessReportPanel excessReportHandler = new ExcessReportPanel();
        JPanel excessReportPanel = excessReportHandler.getExcessReportPanel();
        contentPanel.add(excessReportPanel, "Excess Report");

        JButton btnExcessReport = new JButton("Excess Report");
        btnExcessReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Excess Report");
            }
        });
        navBar.add(btnExcessReport);

        
        // popular pairs
         PopularPairsReportPanel popularPairsHandler = new PopularPairsReportPanel();
        JPanel popularPairsPanel = popularPairsHandler.getPopularPairsReportPanel();
        contentPanel.add(popularPairsPanel, "Popular Pairs");

        JButton btnPopularPairs = new JButton("Popular Pairs");
        btnPopularPairs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Popular Pairs");
            }
        });
        navBar.add(btnPopularPairs);
        

        JPanel trendsPanel = new JPanel(new BorderLayout());
        trendsPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        trendsPanel.setBackground(Color.white);
        trendsPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        trendsPanel.add(navBar, BorderLayout.NORTH);
        trendsPanel.add(contentPanel, BorderLayout.CENTER);

        return trendsPanel;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // Center Horizontal Bar
    // Milk Tea
    /**
     * Creates and returns the central JPanel which manages and displays 
     * different panels based on a card layout.
     * 
     * @return JPanel containing the central display.
     */
    public JPanel centerPanel() {
        // Center panel
        int panelHeight = 300;
        int panelWidth = screenSize.width - (screenSize.width / 5);
        centerPanelCardLayout = new CardLayout();
        centerPanel = new JPanel(centerPanelCardLayout);
        //inventory page panel
        JScrollPane scrollPaneInventoryPage = new JScrollPane(ManagerView.inventoryPage());
        JScrollPane scrollPaneMenuItemPage = new JScrollPane(ManagerView.menuItemPage());
        JScrollPane scrollPaneLowStockPage = new JScrollPane(ManagerView.lowStockPage());

        centerPanel.add(homePage(), "Home Page");
        centerPanel.add(trendsPage(), "Trends Page");
        centerPanel.add(scrollPaneInventoryPage, "Inventory Page");
        centerPanel.add(scrollPaneMenuItemPage, "Menu Item Page");
        centerPanel.add(scrollPaneLowStockPage, "Low Stock Page");
        centerPanel.add(ManagerView.orderHistoryPage() , "Order History Page");
        return centerPanel;
    }

    

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Main constructor for the GUI class. It initializes and sets up the main JFrame
     * for the application, setting up the layout, dimensions, and populating it with 
     * various panels.
     */
    public GUI() {
        JFrame frame = new JFrame();

        frame.setTitle("Sharetea - Glory of Taiwan!"); // sets title of frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
        frame.setSize(screenSize.width, screenSize.height); // sets the x-dimension, and y-dimension of frame
        ImageIcon image = new ImageIcon("assets/sharetealogo.png");
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(255, 255, 255));

        frame.setLayout(new BorderLayout());
        frame.add(rightPanel(), BorderLayout.EAST);
        frame.add(bottomPanel(), BorderLayout.SOUTH);
        frame.add(centerPanel(), BorderLayout.CENTER);

        currentEmployeeLabel.setText("Welcome,  " + employeeName);
        currentEmployeeLabel.setFont(new Font("Calibri ", Font.BOLD, 15));
        currentEmployeeLabel.setForeground(Color.white);
        currentEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        if((employeeInformation.get(Integer.parseInt(employee_id))).get(1).equals("t")){
            current_view = "Manager";
            bottomPanelCardLayout.show(bottomPanel, "Manager View");
            
        }
        else{
            current_view = "Cashier";
            bottomPanelCardLayout.show(bottomPanel, "Cashier View");            
        }
        currentViewLabel.setText("Current View: " + current_view);
        frame.setVisible(true); // make frame visible


    }

    /**
     * The main entry point for the application. This method initializes the GUI.
     * 
     * @param args Not used.
     */
    public static void main(String[] args) {
        new GUI(); // Create an instance of the Main class to initialize the UI
    }
}
