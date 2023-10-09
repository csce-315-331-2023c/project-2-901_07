import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;

public class GUI{
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    ManagerView managerView = new ManagerView();
    CardLayout bottomPanelCardLayout, centerPanelCardLayout;
    JButton checkoutButton, transactionHistoryButton, trendsButton, inventoryButton;
    JButton switchViewButton, toGoButton, addCustomerButton, totalChargeButton, ticketsButton;
    JLabel currentViewLabel;
    JPanel centerPanel, rightPanel, bottomPanel;
    JPanel homePage, inventoryPage;
    
    String current_view = "Cashier";

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // Bottom Horizontal Bar
    // Checkout + Transaction History + Trends + Availability
    public JPanel bottomPanel() {
        // Bottom panel
        int panelHeight = screenSize.height / 10;
        int panelWidth = 0; // value does not matter
        //bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        //bottomPanel.setBackground(Color.gray);
        bottomPanelCardLayout = new CardLayout();
        bottomPanel = new JPanel(bottomPanelCardLayout);
        bottomPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        //cashier view bottom panel
        JPanel cashierView = new JPanel(new GridLayout(1, 5, 30, 10));
        cashierView.setBorder(new EmptyBorder(20, 20, 20, 20));
        cashierView.add(checkout_button());
        cashierView.add(new JLabel());
        cashierView.add(new JLabel());
        cashierView.add(new JLabel());
        //manager view bottom panel
        JPanel managerView = new JPanel(new GridLayout(1, 5, 30, 10));
        managerView.setBorder(new EmptyBorder(20, 20, 20, 20));
        managerView.add(checkout_button());
        managerView.add(transactionHistory_button());
        managerView.add(trends_button());
        managerView.add(inventory_button());

        bottomPanel.add(cashierView, "Cashier View");
        bottomPanel.add(managerView, "Manager View");

        return bottomPanel;
    }
    
    // 1. Checkout Button
    public JButton checkout_button() {
        checkoutButton = new JButton("Checkout");
        checkoutButton.setMargin(new Insets(50, 50, 50, 50));
        checkoutButton.setFont(new Font("Calibri", Font.BOLD, 16));
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("checkoutButton clicked");
            }
        });
        return checkoutButton;
    }

    // 2. Transaction Button
    public JButton transactionHistory_button() {
        transactionHistoryButton = new JButton("Transaction");
        transactionHistoryButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return transactionHistoryButton;
    }

    // 3. Trends Button
    public JButton trends_button() {
        trendsButton = new JButton("Trends");
        trendsButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return trendsButton;
    }

    // 4. Availability Button
    public JButton inventory_button() {
        inventoryButton = new JButton("Inventory");
        inventoryButton.setFont(new Font("Calibri", Font.BOLD, 16));
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("inventoryButton clicked");
                centerPanelCardLayout.show(centerPanel, "Inventory Page");
            }
        });
        return inventoryButton;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    // Right Vertical Bar
    // Switch View + To Go + Charge Total + Tickets
    public JPanel rightPanel() {
        // Right panel
        int panelHeight = 0; // value does not matter
        int panelWidth = screenSize.width / 6;
        JPanel rightPanel = new JPanel(new GridLayout(0, 1, 20, 20));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        rightPanel.setBackground(Color.cyan);
        rightPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        currentViewLabel = new JLabel("Current View: " + current_view);
        currentViewLabel.setFont(new Font("Calibri ", Font.BOLD, 15));
        currentViewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        rightPanel.add(currentViewLabel);
        rightPanel.add(switchView_button());
        rightPanel.add(toGo_button());
        rightPanel.add(addCustomer_button());
        rightPanel.add(totalCharge_button());
        rightPanel.add(tickets_button());
        return rightPanel;
    }

    // 1. Switch View 
    public JButton switchView_button() {
        switchViewButton = new JButton("Switch View");
        switchViewButton.setBounds(200, 100, 100, 50);
        switchViewButton.setFont(new Font("Calibri", Font.BOLD, 16));
        switchViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("switchViewButton clicked");
                if (current_view == "Manager"){
                    current_view = "Cashier";
                }else{
                    current_view = "Manager";
                }
                bottomPanelCardLayout.next(bottomPanel);
                centerPanelCardLayout.show(centerPanel, "Home Page");
                currentViewLabel.setText("Current View: " + current_view);
            }
        });
        return switchViewButton;
    }

    // 2. To Go 
    public JButton toGo_button() {
        toGoButton = new JButton("To Go");
        toGoButton.setBounds(200, 100, 100, 50);
        toGoButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return toGoButton;
    }

    // 3. Add Customer 
    public JButton addCustomer_button() {
        addCustomerButton = new JButton("Add Customer");
        addCustomerButton.setBounds(200, 100, 100, 50);
        addCustomerButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return addCustomerButton;
    }

    // 4. Total Charge 
    public JButton totalCharge_button() {
        totalChargeButton = new JButton("Total Charge");
        totalChargeButton.setBounds(200, 100, 100, 50);
        totalChargeButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return totalChargeButton;
    }

    // 5. Tickets
    public JButton tickets_button() {
        ticketsButton = new JButton("Tickets");
        ticketsButton.setBounds(200, 100, 100, 50);
        ticketsButton.setFont(new Font("Calibri", Font.BOLD, 16));
        return ticketsButton;
    }

    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public JPanel homePage() {
        int panelHeight = 300;
        int panelWidth = screenSize.width - (screenSize.width / 6);
        JPanel homePagePanel = new JPanel(new GridLayout(3, 4, 30, 30));
        homePagePanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        homePagePanel.setBackground(Color.white);
        homePagePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        for (int i = 0; i < 13; i++) {
            JButton rectangle = new JButton();
            rectangle.setBackground(Color.gray); // Set the color of the rectangles
            homePagePanel.add(rectangle);
        }
        return homePagePanel;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // Center Horizontal Bar
    // Milk Tea
    
    public JPanel centerPanel() {
        // Center panel
        int panelHeight = 300;
        int panelWidth = screenSize.width - (screenSize.width / 4);
        centerPanelCardLayout = new CardLayout();
        centerPanel = new JPanel(centerPanelCardLayout);

        centerPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        for (int i = 0; i < 13; i++) {
            JButton categoryButton = new JButton();
            categoryButton.setBackground(Color.gray); // Set the color of the rectangles
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CategoryHandler.categoryHandlerPanel(categoryButton);
                }
            });
            centerPanel.add(categoryButton);
        }
        //inventory page panel
        inventoryPage = new JPanel();
        inventoryPage = ManagerView.inventoryPage(inventoryPage);

        centerPanel.add(homePage(), "Home Page");
        centerPanel.add(inventoryPage, "Inventory Page");

        return centerPanel;
    }

    

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public GUI() {
        // FRAMES TUTORIAL
        JFrame frame = new JFrame();

        frame.setTitle("Sharetea - Glory of Taiwan!"); // sets title of frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
        // frame.setResizable(false); //prevent frame from being resized
        frame.setSize(screenSize.width, screenSize.height); // sets the x-dimension, and y-dimension of frame

        ImageIcon image = new ImageIcon("logo.png");
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(255, 255, 255));

        frame.setLayout(new BorderLayout());
        frame.add(rightPanel(), BorderLayout.EAST);
        frame.add(bottomPanel(), BorderLayout.SOUTH);
        frame.add(centerPanel(), BorderLayout.CENTER);

        frame.setVisible(true); // make frame visible
        // END OF FRAMES TUTORIAL

    }


    

    public static void main(String[] args) {
        new GUI(); // Create an instance of the Main class to initialize the UI
    }
}
