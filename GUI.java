import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;

public class GUI implements ActionListener {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JButton checkoutButton, transactionHistoryButton, trendsButton, availabilityButton;
    JButton switchViewButton, toGoButton, addCustomerButton, totalChargeButton, ticketsButton;
    JLabel currentViewLabel;
    String current_view = "Cashier";

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // Bottom Horizontal Bar
    // Checkout + Transaction History + Trends + Availability
    public JPanel bottomPanel() {
        // Bottom panel
        int panelHeight = screenSize.height / 10;
        int panelWidth = 0; // value does not matter
        JPanel bottomPanel = new JPanel(new GridLayout(1, 10, 20, 20));
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        bottomPanel.setBackground(Color.gray);
        bottomPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        bottomPanel.add(checkout_button());
        bottomPanel.add(transactionHistory_button());
        bottomPanel.add(trends_button());
        bottomPanel.add(availability_button());
        
        //empty space for grid
        return bottomPanel;
    }
    
    // 1. Checkout Button
    public JButton checkout_button() {
        checkoutButton = new JButton("Checkout");
        checkoutButton.setMargin(new Insets(50, 50, 50, 50));
        checkoutButton.setFont(new Font("Calibri", Font.BOLD, 16));
        checkoutButton.addActionListener(this);
        return checkoutButton;
    }

    // 2. Transaction Button
    public JButton transactionHistory_button() {
        transactionHistoryButton = new JButton("Transaction");
        transactionHistoryButton.setFont(new Font("Calibri", Font.BOLD, 16));
        transactionHistoryButton.addActionListener(this);
        return transactionHistoryButton;
    }

    // 3. Trends Button
    public JButton trends_button() {
        trendsButton = new JButton("Trends");
        trendsButton.setFont(new Font("Calibri", Font.BOLD, 16));
        trendsButton.addActionListener(this);
        return trendsButton;
    }

    // 4. Availability Button
    public JButton availability_button() {
        availabilityButton = new JButton("Availability");
        availabilityButton.setFont(new Font("Calibri", Font.BOLD, 16));
        availabilityButton.addActionListener(this);
        return availabilityButton;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    // Right Vertical Bar
    // Switch View + To Go + Charge Total + Tickets
    public JPanel rightPanel() {
        // Right panel
        int panelHeight = 0; // value does not matter
        int panelWidth = screenSize.width / 8;
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
        switchViewButton.addActionListener(this);
        return switchViewButton;
    }

    // 2. To Go 
    public JButton toGo_button() {
        toGoButton = new JButton("To Go");
        toGoButton.setBounds(200, 100, 100, 50);
        toGoButton.setFont(new Font("Calibri", Font.BOLD, 16));
        toGoButton.addActionListener(this);
        return toGoButton;
    }

    // 3. Add Customer 
    public JButton addCustomer_button() {
        addCustomerButton = new JButton("Add Customer");
        addCustomerButton.setBounds(200, 100, 100, 50);
        addCustomerButton.setFont(new Font("Calibri", Font.BOLD, 16));
        addCustomerButton.addActionListener(this);
        return addCustomerButton;
    }

    // 4. Total Charge 
    public JButton totalCharge_button() {
        totalChargeButton = new JButton("Total Charge");
        totalChargeButton.setBounds(200, 100, 100, 50);
        totalChargeButton.setFont(new Font("Calibri", Font.BOLD, 16));
        totalChargeButton.addActionListener(this);
        return totalChargeButton;
    }

    // 5. Tickets
    public JButton tickets_button() {
        ticketsButton = new JButton("Tickets");
        ticketsButton.setBounds(200, 100, 100, 50);
        ticketsButton.setFont(new Font("Calibri", Font.BOLD, 16));
        ticketsButton.addActionListener(this);
        return ticketsButton;
    }

    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // Center Horizontal Bar
    // Milk Tea
    
    public JPanel centerPanel() {
        // Center panel
        int panelHeight = 300;
        int panelWidth = screenSize.width - (screenSize.width / 6);
        JPanel centerPanel = new JPanel(new GridLayout(3, 4, 30, 30));
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(Color.white);
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
        frame.add(centerPanel(), BorderLayout.WEST);

        frame.setVisible(true); // make frame visible
        // END OF FRAMES TUTORIAL

        // LABELS TUTORIAL
        JLabel label = new JLabel(); // create a label
        label.setText("Test"); // set text of label
        frame.add(label);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkoutButton) {
            System.out.println("checkoutButton clicked");
        }
        if (e.getSource() == switchViewButton) {
            System.out.println("switchViewButton clicked");
            if (current_view == "Manager"){
                current_view = "Cashier";
                new CashierView();
            }else{
                current_view = "Manager";
                new ManagerView();
            }
            currentViewLabel.setText("Current View: " + current_view);
        }
    }

    

    public static void main(String[] args) {
        new GUI(); // Create an instance of the Main class to initialize the UI
    }
}
