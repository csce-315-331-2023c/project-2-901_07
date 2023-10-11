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

public class ManagerView {
    // Class contains all methods associated with the pages for the Manager view
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static JFrame frameOpened = new JFrame(null, null);
    static JPanel inventoryPage = new JPanel(new FlowLayout(0));
    static JPanel menuItemPage = new JPanel(new FlowLayout(0));
    static Set<String> drinkTypes = new HashSet<>();
    static JPanel changePricePanel(JButton toppingButton, List<String> topping){
        System.out.println("THIS IS > " + topping.get(0));
		JPanel changePricePanel = new JPanel();
		//changePricePanel.setBackground(new Color(50, 240, 240));
        changePricePanel.setBounds(0, 0, 354, 160);
		changePricePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		changePricePanel.setLayout(null);
	    JLabel lblNewLabel = new JLabel("Current Price: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(34, 51, 126, 14);
		changePricePanel.add(lblNewLabel);
		
		JTextField textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(new Color(192, 192, 192));
        textField.setText("$" + topping.get(1));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(new Color(0, 0, 0));
		textField.setBounds(170, 48, 86, 20);
		changePricePanel.add(textField);
		textField.setColumns(10);
		
		JTextField newPriceTextField = new JTextField();
		newPriceTextField.setColumns(10);
		newPriceTextField.setBounds(170, 79, 86, 20);
        newPriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		changePricePanel.add(newPriceTextField);
		
        JLabel lblNewLabel_1 = new JLabel(topping.get(0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 11, 318, 31);
		changePricePanel.add(lblNewLabel_1);

        JLabel validationLabel = new JLabel();
		validationLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		validationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		validationLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		validationLabel.setBounds(10, 130, 318, 31);
		changePricePanel.add(validationLabel);
		
		JLabel lblNewPrice = new JLabel("New Price: ");
		lblNewPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPrice.setBounds(34, 82, 126, 14);
		changePricePanel.add(lblNewPrice);

		JButton changePrice_Button = new JButton("Change Price");
		changePrice_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String newPrice = newPriceTextField.getText();
                System.out.println("input: \"%s\"".formatted(newPrice));
                if (newPrice.trim().isEmpty()){
                    validationLabel.setForeground(Color.red);
                    validationLabel.setText("ERROR: No price inputted.");
                }
                // TODO : add validation for numbers with 2 decimal places or less
                else{
                    try {
                        float floatValue = Float.parseFloat(newPrice);
                        if (floatValue < 0){
                            System.err.println("Invalid float format: " + newPrice);
                            validationLabel.setForeground(Color.red);
                            validationLabel.setText("ERROR: $%s is an invalid price.".formatted(newPrice));                            
                        }
                        else{
                            System.out.println("Converted float value: " + floatValue);
                            boolean ranSuccessfully = run_SQL_Command("topping", 
                            """
                            UPDATE topping SET price = 
                            %s WHERE name = '%s';
                            """.formatted(newPrice,topping.get(0)));
                            if (ranSuccessfully == true){
                                textField.setText("$%s".formatted(newPrice));
                                validationLabel.setForeground(Color.green);
                                validationLabel.setText("SUCCESS: price changed to $%s.".formatted(newPrice));
                                topping.set(1,newPrice);
                                toppingButton.setText("<html>%s<br>Price: $%s<br>Stock: %s</html>".formatted(topping.get(0),topping.get(1),topping.get(2))); 
                                
                            }
                        }
                    } catch (NumberFormatException e_2) {
                        System.err.println("Invalid float format: " + newPrice);
                        validationLabel.setForeground(Color.red);
                        validationLabel.setText("ERROR: $%s is an invalid price.".formatted(newPrice));
                    }                    
                }



			}
		});
		changePrice_Button.setBounds(75, 107, 181, 23);
		changePricePanel.add(changePrice_Button);
        return changePricePanel;
    }

    static JPanel changeStockPanelTopping(JButton toppingButton, List<String> topping){
		JPanel changePricePanel = new JPanel();
		changePricePanel.setBackground(new Color(144, 44, 62));
        changePricePanel.setBounds(0, 0, 354, 160);
		changePricePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		changePricePanel.setLayout(null);
	    JLabel lblNewLabel = new JLabel("Current Stock: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(34, 51, 126, 14);
		changePricePanel.add(lblNewLabel);
		
		JTextField textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(new Color(192, 192, 192));
        textField.setText(topping.get(2));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(new Color(0, 0, 0));
		textField.setBounds(170, 48, 86, 20);
		changePricePanel.add(textField);
		textField.setColumns(10);
		
		JTextField newPriceTextField = new JTextField();
		newPriceTextField.setColumns(10);
		newPriceTextField.setBounds(170, 79, 86, 20);
        newPriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		changePricePanel.add(newPriceTextField);
		
        JLabel lblNewLabel_1 = new JLabel(topping.get(0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 11, 318, 31);
		changePricePanel.add(lblNewLabel_1);

        JLabel validationLabel = new JLabel();
		validationLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		validationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		validationLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		validationLabel.setBounds(10, 130, 318, 31);
		changePricePanel.add(validationLabel);
		
		JLabel lblNewPrice = new JLabel("New Stock: ");
		lblNewPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPrice.setBounds(34, 82, 126, 14);
		changePricePanel.add(lblNewPrice);

		JButton changePrice_Button = new JButton("Change Stock");
		changePrice_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String newStock = newPriceTextField.getText();
                System.out.println("input: \"%s\"".formatted(newStock));
                if (newStock.trim().isEmpty()){
                    validationLabel.setForeground(Color.red);
                    validationLabel.setText("ERROR: No Stock inputted.");
                }
                // TODO : add validation for numbers with 2 decimal places or less
                else{
                    try {
                        float floatValue = Float.parseFloat(newStock);
                        if(floatValue < 0){
                            System.err.println("Invalid float format: " + newStock);
                            validationLabel.setForeground(Color.red);
                            validationLabel.setText("ERROR: %s is an invalid stock.".formatted(newStock));                            
                        }
                        else{
                            int intValue = Integer.parseInt(newStock);
                            System.out.println("Converted float value: " + intValue);
                            boolean ranSuccessfully = run_SQL_Command("topping", 
                            """
                            UPDATE topping SET availability = 
                            %d WHERE name = '%s';
                            """.formatted(intValue,topping.get(0)));
                            if (ranSuccessfully == true){
                                textField.setText("%s".formatted(newStock));
                                validationLabel.setForeground(Color.green);
                                validationLabel.setText("SUCCESS: stock changed to %s.".formatted(newStock));
                                topping.set(2,newStock);
                                toppingButton.setText("<html>%s<br>Price: $%s<br>Stock: %s</html>".formatted(topping.get(0),topping.get(1),topping.get(2))); 
                                
                            }
                        }
                    } catch (NumberFormatException e_2) {
                        System.err.println("Invalid float format: " + newStock);
                        validationLabel.setForeground(Color.red);
                        validationLabel.setText("ERROR: %s is an invalid stock.".formatted(newStock));
                    }                    
                }



			}
		});
		changePrice_Button.setBounds(75, 107, 181, 23);
		changePricePanel.add(changePrice_Button);
        return changePricePanel;
    }

    static JPanel changePriceMenuItem(JButton menuItemButton, List<String> menuItem){
		JPanel changePricePanel = new JPanel();
		//changePricePanel.setBackground();
        changePricePanel.setBounds(0, 0, 354, 160);
		changePricePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		changePricePanel.setLayout(null);
	    JLabel lblNewLabel = new JLabel("Current Price: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(34, 51, 126, 14);
		changePricePanel.add(lblNewLabel);
		
		JTextField textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(new Color(192, 192, 192));
        textField.setText("$" + menuItem.get(2));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(new Color(0, 0, 0));
		textField.setBounds(170, 48, 86, 20);
		changePricePanel.add(textField);
		textField.setColumns(10);
		
		JTextField newPriceTextField = new JTextField();
		newPriceTextField.setColumns(10);
		newPriceTextField.setBounds(170, 79, 86, 20);
        newPriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		changePricePanel.add(newPriceTextField);
		
        JLabel lblNewLabel_1 = new JLabel(menuItem.get(0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 11, 318, 31);
		changePricePanel.add(lblNewLabel_1);

        JLabel validationLabel = new JLabel();
		validationLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		validationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		validationLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		validationLabel.setBounds(10, 130, 318, 31);
		changePricePanel.add(validationLabel);
		
		JLabel lblNewPrice = new JLabel("New Price: ");
		lblNewPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPrice.setBounds(34, 82, 126, 14);
		changePricePanel.add(lblNewPrice);

		JButton changePrice_Button = new JButton("Change Price");
		changePrice_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String newPrice = newPriceTextField.getText();
                System.out.println("input: \"%s\"".formatted(newPrice));
                if (newPrice.trim().isEmpty()){
                    validationLabel.setForeground(Color.red);
                    validationLabel.setText("ERROR: No price inputted.");
                }
                // TODO : add validation for numbers with 2 decimal places or less
                else{
                    try {
                        float floatValue = Float.parseFloat(newPrice);
                        if (floatValue < 0){
                            System.err.println("Invalid float format: " + newPrice);
                            validationLabel.setForeground(Color.red);
                            validationLabel.setText("ERROR: $%s is an invalid price.".formatted(newPrice));                            
                        }
                        else{
                            System.out.println("Converted float value: " + floatValue);
                            boolean ranSuccessfully = run_SQL_Command("topping", 
                            """
                            UPDATE menu_item SET price = 
                            %s WHERE name = '%s';
                            """.formatted(newPrice,menuItem.get(0)));
                            if (ranSuccessfully == true){
                                textField.setText("$%s".formatted(newPrice));
                                validationLabel.setForeground(Color.green);
                                validationLabel.setText("SUCCESS: price changed to $%s.".formatted(newPrice));
                                menuItem.set(2,newPrice);
                                menuItemButton.setText("<html>%s<br>Type: %s<br>Price: $%s</html>".formatted(menuItem.get(0),menuItem.get(1),menuItem.get(2))); 
                                //TODO
                            }
                        }
                    } catch (NumberFormatException e_2) {
                        System.err.println("Invalid float format: " + newPrice);
                        validationLabel.setForeground(Color.red);
                        validationLabel.setText("ERROR: $%s is an invalid price.".formatted(newPrice));
                    }                    
                }



			}
		});
		changePrice_Button.setBounds(75, 107, 181, 23);
		changePricePanel.add(changePrice_Button);
        return changePricePanel;        
    }

  static JPanel changeStockPanelIngredient(JButton ingredientButton, List<String> ingredient){
		JPanel changePricePanel = new JPanel();
		//changePricePanel.setBackground(new Color(50, 240, 240));
        changePricePanel.setBounds(0, 0, 354, 160);
		changePricePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		changePricePanel.setLayout(null);
	    JLabel lblNewLabel = new JLabel("Current Stock: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(34, 51, 126, 14);
		changePricePanel.add(lblNewLabel);
		
		JTextField textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(new Color(192, 192, 192));
        textField.setText(ingredient.get(1));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(new Color(0, 0, 0));
		textField.setBounds(170, 48, 86, 20);
		changePricePanel.add(textField);
		textField.setColumns(10);
		
		JTextField newPriceTextField = new JTextField();
		newPriceTextField.setColumns(10);
		newPriceTextField.setBounds(170, 79, 86, 20);
        newPriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		changePricePanel.add(newPriceTextField);
		
        JLabel lblNewLabel_1 = new JLabel(ingredient.get(0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 11, 318, 31);
		changePricePanel.add(lblNewLabel_1);

        JLabel validationLabel = new JLabel();
		validationLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		validationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		validationLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		validationLabel.setBounds(10, 130, 318, 31);
		changePricePanel.add(validationLabel);
		
		JLabel lblNewPrice = new JLabel("New Stock: ");
		lblNewPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPrice.setBounds(34, 82, 126, 14);
		changePricePanel.add(lblNewPrice);

        
		JButton changePrice_Button = new JButton("Change Stock");
		changePrice_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String newStock = newPriceTextField.getText();
                System.out.println("input: \"%s\"".formatted(newStock));
                if (newStock.trim().isEmpty()){
                    validationLabel.setForeground(Color.red);
                    validationLabel.setText("ERROR: No Stock inputted.");
                }
                else{
                    try {
                        float floatValue = Float.parseFloat(newStock);
                        if(floatValue < 0){
                            System.err.println("Invalid float format: " + newStock);
                            validationLabel.setForeground(Color.red);
                            validationLabel.setText("ERROR: %s is an invalid stock.".formatted(newStock));                            
                        }
                        else{
                            int intValue = Integer.parseInt(newStock);
                            System.out.println("Converted float value: " + intValue);
                            boolean ranSuccessfully = run_SQL_Command("topping", 
                            """
                            UPDATE ingredients SET availability = 
                            %d WHERE name = '%s';
                            """.formatted(intValue,ingredient.get(0)));
                            if (ranSuccessfully == true){
                                textField.setText("%s".formatted(newStock));
                                validationLabel.setForeground(Color.green);
                                validationLabel.setText("SUCCESS: stock changed to %s.".formatted(newStock));
                                ingredient.set(1,newStock);
                                ingredientButton.setText("<html>%s<br>Stock:%s</html>".formatted(ingredient.get(0),ingredient.get(1))); 
                                
                            }
                        }

                    } catch (NumberFormatException e_2) {
                        System.err.println("Invalid float format: " + newStock);
                        validationLabel.setForeground(Color.red);
                        validationLabel.setText("ERROR: %s is an invalid stock.".formatted(newStock));
                    }                    
                }



			}
		});
		changePrice_Button.setBounds(75, 107, 181, 23);
		changePricePanel.add(changePrice_Button);
        return changePricePanel;
    }
 //TODO
    static JFrame openAddMenuItemFrame(JButton addMenuItemButton){
        JFrame openAddMenuItemFrame = new JFrame();
		openAddMenuItemFrame.setBounds(100, 100, 379, 290);
        openAddMenuItemFrame.setVisible(true); // Display the new frame
        openAddMenuItemFrame.setResizable(false);
        openAddMenuItemFrame.setLocationRelativeTo(null);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		openAddMenuItemFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel itemNameLabel = new JLabel("Menu Item Name:  ");
		itemNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		itemNameLabel.setBounds(10, 46, 137, 14);
		contentPane.add(itemNameLabel);
		
		JTextField itemNameTextField = new JTextField();
		itemNameTextField.setBounds(153, 43, 135, 20);
		contentPane.add(itemNameTextField);
		itemNameTextField.setColumns(10);

		Choice choice = new Choice();
		choice.setBounds(153, 79, 135, 20);
        for(String drinkType:drinkTypes){
            choice.add(drinkType);
        }
        contentPane.add(choice);
    
    
		
		JLabel lblItemType = new JLabel("Menu Item Type:  ");
		lblItemType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblItemType.setBounds(10, 79, 137, 14);
		contentPane.add(lblItemType);
		
		JLabel lblPrice = new JLabel("Price:  ");
		lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice.setBounds(10, 116, 137, 14);
		contentPane.add(lblPrice);
		
		JTextField priceTextField = new JTextField();
		priceTextField.setColumns(10);
		priceTextField.setBounds(153, 113, 135, 20);
		contentPane.add(priceTextField);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Add Menu Item");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(10, 11, 343, 14);
		contentPane.add(lblNewLabel_1);
		
        JLabel inputValidationLabel = new JLabel("");
		inputValidationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inputValidationLabel.setBounds(10, 228, 343, 14);
		contentPane.add(inputValidationLabel);

		JButton createItemButton = new JButton("Create Menu Item");
		createItemButton.setBounds(68, 186, 220, 31);
		createItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String itemName = itemNameTextField.getText();
                String itemType = choice.getSelectedItem();
                String itemPrice = priceTextField.getText();
                
                if(itemName.isEmpty() || itemPrice.isEmpty() ){
                    inputValidationLabel.setText("ERROR: One or more of the fields are empty.");
                    inputValidationLabel.setForeground(Color.red);
                }
                else{
                    try {
                        boolean ranSuccessfully = run_SQL_Command("topping", 
                        """
                        INSERT INTO menu_item(name, type, price)
                        VALUES ('%s','%s',%s);
                        """.formatted(itemName,itemType, itemPrice));
                        priceTextField.setText("");

                        List<String> topping = new ArrayList<>();
                        topping.add(itemName);
                        topping.add(itemPrice);
                        JButton toppingButton = new JButton( "<html>%s<br>Price: $%s<br>Stock: %s</html>".formatted(topping.get(0),topping.get(1),topping.get(2)));
                        toppingButton.setPreferredSize(new Dimension(170, 70));
                        toppingButton.setBackground(Color.gray);
                            toppingButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println(topping.get(0));
                                    frameOpened.dispose();
                                    toppingButton.setBackground(Color.gray);
                                    frameOpened = openToppingFrame(toppingButton, topping);
                                }
                            });
                        inventoryPage.add(toppingButton);
                        itemNameTextField.setText("");
                        inputValidationLabel.setText("SUCCESS: successfully added item.");
                        inputValidationLabel.setForeground(Color.green);
                    } catch (NumberFormatException e_2) {
                        inputValidationLabel.setForeground(Color.red);
                        inputValidationLabel.setText("ERROR: invalid input");
                    }   
                }            

            }
        });
        
		contentPane.add(createItemButton);

        
        choice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (choice.getSelectedItem().equals("ingredient")) {
                    // Perform your action here
                    priceTextField.setEditable(false);
                    priceTextField.setText("");
                    priceTextField.setBackground(new Color(192, 192, 192));
                }
                else if (choice.getSelectedItem().equals("topping")){
                    priceTextField.setEditable(true);
                    priceTextField.setBackground(new Color(255,255,255));
                }
            }
        });

        return openAddMenuItemFrame;        
    }

    static JFrame openAddItemFrame(JButton addItemButton){
        JFrame openAddItemFrame = new JFrame();
		openAddItemFrame.setBounds(100, 100, 379, 290);
        openAddItemFrame.setVisible(true); // Display the new frame
        openAddItemFrame.setResizable(false);
        openAddItemFrame.setLocationRelativeTo(null);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		openAddItemFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel itemNameLabel = new JLabel("Item Name:  ");
		itemNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		itemNameLabel.setBounds(10, 46, 137, 14);
		contentPane.add(itemNameLabel);
		
		JTextField itemNameTextField = new JTextField();
		itemNameTextField.setBounds(153, 43, 135, 20);
		contentPane.add(itemNameTextField);
		itemNameTextField.setColumns(10);

		Choice choice = new Choice();
		choice.setBounds(153, 79, 135, 20);
		choice.add("topping");
        choice.add("ingredient");
        contentPane.add(choice);
    
    
		
		JLabel lblItemType = new JLabel("Item Type:  ");
		lblItemType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblItemType.setBounds(10, 79, 137, 14);
		contentPane.add(lblItemType);
		
		JLabel lblPrice = new JLabel("Price:  ");
		lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice.setBounds(10, 116, 137, 14);
		contentPane.add(lblPrice);
		
		JTextField priceTextField = new JTextField();
		priceTextField.setColumns(10);
		priceTextField.setBounds(153, 113, 135, 20);
		contentPane.add(priceTextField);
		
		JLabel lblStock = new JLabel("Stock:  ");
		lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStock.setBounds(10, 151, 137, 14);
		contentPane.add(lblStock);
		
		JTextField stockTextField = new JTextField();
		stockTextField.setColumns(10);
		stockTextField.setBounds(153, 148, 135, 20);
		contentPane.add(stockTextField);
		
		JLabel lblNewLabel_1 = new JLabel("Add Item");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(10, 11, 343, 14);
		contentPane.add(lblNewLabel_1);
		
        JLabel inputValidationLabel = new JLabel("");
		inputValidationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inputValidationLabel.setBounds(10, 228, 343, 14);
		contentPane.add(inputValidationLabel);

		JButton createItemButton = new JButton("Create Item");
		createItemButton.setBounds(68, 186, 220, 31);
		createItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String itemName = itemNameTextField.getText();
                String itemType = choice.getSelectedItem();
                String itemStock = stockTextField.getText();
                
                if(itemName.isEmpty() || itemStock.isEmpty()){
                    inputValidationLabel.setText("ERROR: One or more of the fields are empty.");
                    inputValidationLabel.setForeground(Color.red);
                }
                else{
                    try {
                        int stockIntValue = Integer.parseInt(itemStock);
                        if(itemType == "topping"){
                            String itemPrice = priceTextField.getText();
                            Float itemPriceFloatValue = Float.parseFloat(itemPrice);
                            boolean ranSuccessfully = run_SQL_Command("topping", 
                            """
                            INSERT INTO topping (name, price, availability)
                            VALUES ('%s',%.2f,%d);
                            """.formatted(itemName,itemPriceFloatValue, stockIntValue));
                            priceTextField.setText("");

                            List<String> topping = new ArrayList<>();
                            topping.add(itemName);
                            topping.add(itemPrice);
                            topping.add(itemStock);
                            JButton toppingButton = new JButton( "<html>%s<br>Price: $%s<br>Stock: %s</html>".formatted(topping.get(0),topping.get(1),topping.get(2)));
                            toppingButton.setPreferredSize(new Dimension(170, 70));
                            toppingButton.setBackground(Color.gray);
                                toppingButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        System.out.println(topping.get(0));
                                        frameOpened.dispose();
                                        toppingButton.setBackground(Color.gray);
                                        frameOpened = openToppingFrame(toppingButton, topping);
                                    }
                                });
                            inventoryPage.add(toppingButton);
                            
                            //TODO: ADD NEW WINDOW
                        }
                        else{//if itemType is ingredient
                            boolean ranSuccessfully = run_SQL_Command("ingredient", 
                            """
                            INSERT INTO ingredients (name, availability)
                            VALUES ('%s',%d);
                            """.formatted(itemName, stockIntValue));
                            List<String> ingredient = new ArrayList<>();
                            ingredient.add(itemName);
                            ingredient.add(itemStock);
                            JButton ingredientsButton = new JButton( "<html>%s<br>Stock:%s</html>".formatted(ingredient.get(0),ingredient.get(1)));
                            ingredientsButton.setPreferredSize(new Dimension(170, 70));
                            ingredientsButton.setBackground(Color.gray);
                                ingredientsButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        System.out.println(ingredient.get(0));
                                        frameOpened.dispose();
                                        ingredientsButton.setBackground(Color.gray);
                                        frameOpened = openIngredientFrame(ingredientsButton,ingredient);
                                    }
                                });
                            inventoryPage.add(ingredientsButton);
                            //TODO: ADD NEW WINDOW
                        }
                        itemNameTextField.setText("");
                        stockTextField.setText("");
                        inputValidationLabel.setText("SUCCESS: successfully added item.");
                        inputValidationLabel.setForeground(Color.green);
                    } catch (NumberFormatException e_2) {
                        inputValidationLabel.setForeground(Color.red);
                        inputValidationLabel.setText("ERROR: invalid input");
                    }   
                }            

            }
        });
        
		contentPane.add(createItemButton);

        
        choice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (choice.getSelectedItem().equals("ingredient")) {
                    // Perform your action here
                    priceTextField.setEditable(false);
                    priceTextField.setText("");
                    priceTextField.setBackground(new Color(192, 192, 192));
                }
                else if (choice.getSelectedItem().equals("topping")){
                    priceTextField.setEditable(true);
                    priceTextField.setBackground(new Color(255,255,255));
                }
            }
        });

        return openAddItemFrame;
    }

    static JFrame openMenuItemFrame(JButton menuItemButton, List<String> menuItem) {
		JFrame menuItemFrame = new JFrame();
        menuItemFrame.setLayout(new BorderLayout(0, 0));
        menuItemFrame.setVisible(true); // Display the new frame
        menuItemFrame.setResizable(false);
		menuItemFrame.setTitle("Menu Item");
		menuItemFrame.setBounds(100, 100, 354, 250);
        menuItemFrame.setLocationRelativeTo(null);
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(144, 44, 62));
        topPanel.setPreferredSize(new Dimension(354, 160));
        menuItemFrame.add(topPanel, BorderLayout.NORTH);
        CardLayout contentPanelCardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(contentPanelCardLayout);
        contentPanel.setPreferredSize(new Dimension(354,160));
        //inventory page panel

        JPanel changeStockPanelIngredient = changePriceMenuItem( menuItemButton, menuItem);
        // //Add different pages
        contentPanel.add(changeStockPanelIngredient, "Menu Item Page");
        // centerPanel.add(inventoryPage, "Inventory Page");

        topPanel.add(contentPanel);
        //changePriceFrame.setContentPane(changePricePanel(topping));

        ///////BOTTOM PANEL COMPONENTS//////////////

        //adjustStock_Button
        JButton adjustStock_Button = new JButton("Price");
		adjustStock_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                contentPanelCardLayout.show(contentPanel, "Menu Item Page");
            }
        });



        JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(240, 40, 240));
        bottomPanel.setBounds(0, 160, 338, 55);

		bottomPanel.setLayout(new GridLayout(1, 3, 0, 0));
        bottomPanel.add(adjustStock_Button);
        menuItemFrame.add(bottomPanel);
    
        /////////////////////////////////////////////////////////////////////
		
        return menuItemFrame;
        }

    static JFrame openToppingFrame(JButton toppingButton, List<String> topping) {
		JFrame changePriceFrame = new JFrame();
        changePriceFrame.setLayout(new BorderLayout(0, 0));
        changePriceFrame.setVisible(true); // Display the new frame
        changePriceFrame.setResizable(false);
		changePriceFrame.setTitle("Topping");
		changePriceFrame.setBounds(100, 100, 354, 250);
        changePriceFrame.setLocationRelativeTo(null);
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(144, 44, 62));
        topPanel.setPreferredSize(new Dimension(354, 160));
        changePriceFrame.add(topPanel, BorderLayout.NORTH);
        CardLayout contentPanelCardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(contentPanelCardLayout);
        contentPanel.setPreferredSize(new Dimension(354,160));
        //inventory page panel
        JPanel changePricePanel = changePricePanel( toppingButton, topping);
        JPanel changeStockPanelTopping = changeStockPanelTopping( toppingButton, topping);

        // //Add different pages
        contentPanel.add(changePricePanel, "Price Page");
        contentPanel.add(changeStockPanelTopping, "Stock Page");
        // centerPanel.add(inventoryPage, "Inventory Page");

        topPanel.add(contentPanel);
        //changePriceFrame.setContentPane(changePricePanel(topping));

        ///////BOTTOM PANEL COMPONENTS//////////////

        //adjustPrice_button
        JButton adjustPrice_Button = new JButton("Price");
		adjustPrice_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                contentPanelCardLayout.show(contentPanel, "Price Page");
            }
        });
        //adjustStock_Button
        JButton adjustStock_Button = new JButton("Stock");
		adjustStock_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                contentPanelCardLayout.show(contentPanel, "Stock Page");
            }
        });

        //deleteItem_Button
        JButton deleteItem_Button = new JButton("Delete Item");
        deleteItem_Button.setForeground(Color.white);
        deleteItem_Button.setBackground(Color.red);
		deleteItem_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                boolean ranSuccessfully = run_SQL_Command("topping", 
                """
                DELETE FROM topping
                WHERE name = '%s';
                """.formatted(topping.get(0)));
            }
        });
     


        JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(240, 40, 240));
        bottomPanel.setBounds(0, 160, 338, 55);

		bottomPanel.setLayout(new GridLayout(1, 3, 0, 0));
        bottomPanel.add(adjustPrice_Button);
        bottomPanel.add(adjustStock_Button);
        //bottomPanel.add(deleteItem_Button);
        changePriceFrame.add(bottomPanel);
    
        /////////////////////////////////////////////////////////////////////
		
        return changePriceFrame;
 
 
    }
    

    static JFrame openIngredientFrame(JButton ingredientButton, List<String> ingredient) {
		JFrame changeStockFrame = new JFrame();
        changeStockFrame.setLayout(new BorderLayout(0, 0));
        changeStockFrame.setVisible(true); // Display the new frame
        changeStockFrame.setResizable(false);
		changeStockFrame.setTitle("Ingredient");
		changeStockFrame.setBounds(100, 100, 354, 250);
        changeStockFrame.setLocationRelativeTo(null);
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(144, 44, 62));
        topPanel.setPreferredSize(new Dimension(354, 160));
        changeStockFrame.add(topPanel, BorderLayout.NORTH);
        CardLayout contentPanelCardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(contentPanelCardLayout);
        contentPanel.setPreferredSize(new Dimension(354,160));
        //inventory page panel

        JPanel changeStockPanelIngredient = changeStockPanelIngredient( ingredientButton, ingredient);
        // //Add different pages
        contentPanel.add(changeStockPanelIngredient, "Stock Page");
        // centerPanel.add(inventoryPage, "Inventory Page");

        topPanel.add(contentPanel);
        //changePriceFrame.setContentPane(changePricePanel(topping));

        ///////BOTTOM PANEL COMPONENTS//////////////

        //adjustStock_Button
        JButton adjustStock_Button = new JButton("Stock");
		adjustStock_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                contentPanelCardLayout.show(contentPanel, "Stock Page");
            }
        });

        //deleteItem_Button
        JButton deleteItem_Button = new JButton("Delete Item");
        deleteItem_Button.setForeground(Color.white);
        deleteItem_Button.setBackground(Color.red);
		deleteItem_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                boolean ranSuccessfully = run_SQL_Command("topping", 
                """
                DELETE FROM ingredient
                WHERE name = '%s';
                """.formatted(ingredient.get(0)));
            }
        });
     


        JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(240, 40, 240));
        bottomPanel.setBounds(0, 160, 338, 55);

		bottomPanel.setLayout(new GridLayout(1, 3, 0, 0));
        bottomPanel.add(adjustStock_Button);
        //bottomPanel.add(deleteItem_Button);
        changeStockFrame.add(bottomPanel);
    
        /////////////////////////////////////////////////////////////////////
		
        return changeStockFrame;
 
 
    }

//TODO
    public static JPanel menuItemPage(){
        JButton addItemButton = new JButton("<html><center>+<br>Add Item</center></html>");
        addItemButton.setPreferredSize(new Dimension(170, 70));
        addItemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameOpened.dispose();
                    frameOpened = openAddMenuItemFrame(addItemButton);
                }
            });        
        menuItemPage.add(addItemButton);
        drinkTypes.clear();
        menuItemPage.setBorder(new EmptyBorder(40, 80, 40, 40));
        menuItemPage.setBackground(Color.white);
        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("type");
        columnNames.add("price");
        List<List<String>> menuItems = query_SQL("menu_item", columnNames); 
        for (List<String> menuItem : menuItems) {
          drinkTypes.add(menuItem.get(1));
          JButton menuItemButton = new JButton( "<html>%s<br>Type: %s<br>Price: $%s</html>".formatted(menuItem.get(0),menuItem.get(1),menuItem.get(2)));
          menuItemButton.setPreferredSize(new Dimension(170, 70));
          menuItemButton.setBackground(Color.cyan);
            menuItemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(menuItem.get(0));
                    frameOpened.dispose();
                    frameOpened = openMenuItemFrame(menuItemButton, menuItem);
                    System.out.println(drinkTypes);
                }
            });
          menuItemPage.add(menuItemButton);
        }
        

        return menuItemPage;       
    }

    public static JPanel inventoryPage(){
        //Method contains commands to format inventory page

        inventoryPage.setBorder(new EmptyBorder(40, 80, 40, 40));
        inventoryPage.setBackground(Color.white);
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
                    frameOpened = openAddItemFrame(addItemButton);
                }
            });        
        inventoryPage.add(addItemButton);

        List<List<String>> toppings = query_SQL("topping", columnNames);
        for (List<String> topping : toppings) {
          JButton toppingButton = new JButton( "<html>%s<br>Price: $%s<br>Stock: %s</html>".formatted(topping.get(0),topping.get(1),topping.get(2)));
          toppingButton.setPreferredSize(new Dimension(170, 70));
          toppingButton.setBackground(Color.green);
            toppingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(topping.get(0));
                    frameOpened.dispose();
                    frameOpened = openToppingFrame(toppingButton, topping);
                }
            });
          inventoryPage.add(toppingButton);
        }

        columnNames.clear();
        columnNames.add("name");
        columnNames.add("availability");

        List<List<String>> ingredients = query_SQL("ingredients", columnNames);
        for (List<String> ingredient : ingredients) {
          JButton ingredientsButton = new JButton( "<html>%s<br>Stock:%s</html>".formatted(ingredient.get(0),ingredient.get(1)));
          ingredientsButton.setPreferredSize(new Dimension(170, 70));
          ingredientsButton.setBackground(Color.yellow);
            ingredientsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(ingredient.get(0));
                    frameOpened.dispose();
                    frameOpened = openIngredientFrame(ingredientsButton,ingredient);
                }
            });
          inventoryPage.add(ingredientsButton);
        }

        return inventoryPage;
        
    }

    public static boolean run_SQL_Command(String tableName, String command){
     //Building the connection with your credentials
        Connection conn = null;
        String teamName = "01g";
        String dbName = "csce315331_"+teamName+"_db";
        // "csce315331_01g_db"
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup();

        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println("\nOpened database successfully");
        System.out.println("Executing SQL Command: \"%s\"".formatted(command));

        try{
            Statement createStmt = conn.createStatement();
            boolean executed = conn.createStatement().execute(command);
            System.out.println("Command executed successfully \n");
        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        //closing the connection
        try {
            conn.close();
            System.out.println("Connection Closed.\n\n");
            return true;
        } catch(Exception e) {
            System.out.println("Connection NOT Closed.");
        }//end try catch
        return false;
    }
    
    public static List<List<String>> query_SQL(String tableName, List<String> columnNames){
     //Building the connection with your credentials
        Connection conn = null;
        String teamName = "01g";
        String dbName = "csce315331_"+teamName+"_db";
        // "csce315331_01g_db"
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup();

        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");

        try{
            Statement createStmt = conn.createStatement();
            
            String selectTable = """
            SELECT *
            FROM %s
            ORDER BY name ASC;
            """.formatted(tableName);
            ResultSet result = conn.createStatement().executeQuery(selectTable);
            List<List<String>> output = new ArrayList<>();
            System.out.println("--------------------Query Results--------------------");
            while (result.next()) {
               List<String> rowInfo = new ArrayList<>();
               for (String columnName: columnNames) {
                    rowInfo.add(result.getString(columnName));
               }
               output.add(rowInfo);
            }
            System.out.println(output);
            System.out.println(result);
            return output;
        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        //closing the connection
        try {
            conn.close();
            System.out.println("Connection Closed.");
        } catch(Exception e) {
            System.out.println("Connection NOT Closed.");
        }//end try catch
        return null;
    }





     ManagerView(){

     //Building the connection with your credentials
     }
}

