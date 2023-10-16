import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AddItemInventoryFrame {
    public JFrame frame;
	public JPanel contentPanel;
    public JButton addItemButton;
    public AddItemInventoryFrame(JButton addItemButton) {
        this.frame = new JFrame();
        this.addItemButton = addItemButton;
        frame.setBounds(100, 100, 379, 290);
        frame.setVisible(true); // Display the new frame
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frame.setContentPane(contentPane);
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
                //System.out.println("itenName: " + itemName);
                //System.out.println("itenType: " + itemType);
                //System.out.println("itenStock: " + itemStock);
                List<String> columnNames = new ArrayList<>();
                columnNames.add("name");
                List<List<String>> result = DatabaseHandler.query_SQL( 
                """
                    SELECT name FROM topping WHERE LOWER(name) = LOWER('%s')
                    UNION
                    SELECT name FROM ingredients WHERE LOWER(name) = LOWER('%s');
                """.formatted(itemName,itemName), columnNames);
                if(itemName.equals("") || itemStock.equals("")){
                    inputValidationLabel.setText("ERROR: One or more of the fields are empty.");
                    inputValidationLabel.setForeground(Color.red);
                }
                else{
                    try{
                        float floatValueItemStock = Float.parseFloat(itemStock);
                        if (!result.isEmpty()){
                            inputValidationLabel.setText("ERROR: Item already exist in inventory");
                            inputValidationLabel.setForeground(Color.red);                        
                        }
                        else if (floatValueItemStock < 0){
                            System.err.println("Invalid float format: " + itemStock);
                            inputValidationLabel.setForeground(Color.red);
                            inputValidationLabel.setText("ERROR: %s is an invalid stock.".formatted(itemStock));                       
                        }
                        else{
                            try {
                                
                                int stockIntValue = Integer.parseInt(itemStock);
                                if(itemType == "topping"){
                                    String itemPrice = priceTextField.getText();
                                    try{
                                        Float itemPriceFloatValue = Float.parseFloat(itemPrice);
                                        if (itemPriceFloatValue < 0){
                                            System.err.println("Invalid float format: " + itemPriceFloatValue);
                                            inputValidationLabel.setForeground(Color.red);
                                            inputValidationLabel.setText("ERROR: $%s is an invalid price.".formatted(itemPriceFloatValue));                            
                                        }
                                        else{
                                            boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("topping", 
                                                """
                                                INSERT INTO topping (name, price, availability)
                                                VALUES ('%s',%.2f,%d);
                                                """.formatted(itemName,itemPriceFloatValue, stockIntValue));
                                                priceTextField.setText("");

                                                List<String> topping = new ArrayList<>();
                                                topping.add("");
                                                topping.add(itemName);
                                                topping.add(itemPrice);
                                                topping.add(itemStock);
                                                JButton toppingButton = new JButton( "<html>%s<br>Price: $%s<br>Stock: %s</html>".formatted(topping.get(1),topping.get(2),topping.get(3)));
                                                toppingButton.setPreferredSize(new Dimension(170, 70));
                                                toppingButton.setBackground(Color.pink);
                                                    toppingButton.addActionListener(new ActionListener() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent e) {
                                                            //System.out.println(topping.get(0));
                                                            ManagerView.frameOpened.dispose();
                                                            toppingButton.setBackground(Color.pink);
                                                            ToppingInventoryFrame tempToppingInventoryFrame = new ToppingInventoryFrame(toppingButton, topping);
                                                            ManagerView.frameOpened = tempToppingInventoryFrame.frame;
                                                        }
                                                    });
                                                ManagerView.inventoryPage.add(toppingButton);
                                                DatabaseHandler.updateToppings();
                                        }
                                    }catch (NumberFormatException e_2) {
                                        System.err.println("Invalid float format: " + itemPrice);
                                        inputValidationLabel.setForeground(Color.red);
                                        inputValidationLabel.setText("ERROR: $%s is an invalid price.".formatted(itemPrice));
                                    }     
        
                                    
                                    //TODO: ADD NEW WINDOW
                                }
                                else{//if itemType is ingredient
                                    boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("ingredient", 
                                    """
                                    INSERT INTO ingredients (name, availability)
                                    VALUES ('%s',%d);
                                    """.formatted(itemName, stockIntValue));
                                    List<String> ingredient = new ArrayList<>();
                                    ingredient.add("");
                                    ingredient.add(itemName);
                                    ingredient.add(itemStock);
                                    JButton ingredientsButton = new JButton( "<html>%s<br>Stock:%s</html>".formatted(ingredient.get(1),ingredient.get(2)));
                                    ingredientsButton.setPreferredSize(new Dimension(170, 70));
                                    ingredientsButton.setBackground(Color.pink);
                                        ingredientsButton.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                //System.out.println(ingredient.get(0));
                                                ManagerView.frameOpened.dispose();
                                                ingredientsButton.setBackground(Color.pink);
                                                IngredientInventoryFrame tempIngredientInventoryFrame = new IngredientInventoryFrame(ingredientsButton, ingredient);
                                                ManagerView.frameOpened = tempIngredientInventoryFrame.frame;
                                            }
                                        });
                                    ManagerView.inventoryPage.add(ingredientsButton);
                                    //TODO: ADD NEW WINDOW
                                    DatabaseHandler.updateIngredients();
                                }
                                itemNameTextField.setText("");
                                stockTextField.setText("");
                                ManagerView.inventoryPage.revalidate();
                                ManagerView.inventoryPage.repaint();
                                inputValidationLabel.setText("SUCCESS: successfully added item.");
                                inputValidationLabel.setForeground(Color.green);
                    
                            } catch (NumberFormatException e_2) {
                                inputValidationLabel.setForeground(Color.red);
                                inputValidationLabel.setText("ERROR: invalid input");
                            }   
                        }
                    }catch (NumberFormatException e_2) {
                        System.err.println("Invalid float format: " + itemStock);
                        inputValidationLabel.setForeground(Color.red);
                        inputValidationLabel.setText("ERROR: %s is an invalid stock.".formatted(itemStock));
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

    }

}
