import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class AddMenuItemFrame {
    public JFrame frame;
	public JPanel contentPanel;
    public JButton addMenuItemButton;

   public AddMenuItemFrame(JButton addMenuItemButton){
        this.frame = new JFrame("Scrollable List Example");
        frame.setLayout(null);
        frame.setSize(500, 620);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        frame.setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel itemNameLabel = new JLabel("Menu Item Name:  ");
        itemNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        itemNameLabel.setBounds(80, 46, 137, 14);
        contentPane.add(itemNameLabel);

        JTextField itemNameTextField = new JTextField();
        itemNameTextField.setBounds(223, 43, 135, 20);
        contentPane.add(itemNameTextField);
        itemNameTextField.setColumns(10);

        Choice choice = new Choice();
        choice.setBounds(223, 79, 135, 20);
        for(List<String> drinkType:DatabaseHandler.drinkTypes){
            choice.add(drinkType.get(0));
        }
        contentPane.add(choice);

        JLabel lblItemType = new JLabel("Menu Item Type:  ");
        lblItemType.setHorizontalAlignment(SwingConstants.RIGHT);
        lblItemType.setBounds(80, 79, 137, 14);
        contentPane.add(lblItemType);

        JLabel lblPrice = new JLabel("Price:  ");
        lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPrice.setBounds(80, 116, 137, 14);
        contentPane.add(lblPrice);

        JTextField priceTextField = new JTextField();
        priceTextField.setColumns(10);
        priceTextField.setBounds(223, 113, 135, 20);
        contentPane.add(priceTextField);

        JLabel lblNewLabel_1 = new JLabel("Add Menu Item");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setHorizontalTextPosition(SwingConstants.LEFT);
        lblNewLabel_1.setBounds(0, 15, 500, 14);
        contentPane.add(lblNewLabel_1);

        JLabel inputValidationLabel = new JLabel("");
        inputValidationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputValidationLabel.setBounds(80, 540, 343, 14);
        contentPane.add(inputValidationLabel);


        // Create a DefaultListModel and add the array elements to it
        DefaultListModel<String> allIngredientListModel = new DefaultListModel<>();
        for (String ingredient : DatabaseHandler.ingredientNames) {
            allIngredientListModel.addElement(ingredient);
        }
        
        // Create a JList with the items
        JList<String> ingredientList = new JList<>(allIngredientListModel);
        // all ingredients list
        JScrollPane scrollPane = new JScrollPane(ingredientList);
        scrollPane.setBounds(50, 190, 180, 250);
        frame.add(scrollPane);

        DefaultListModel<String> newDrinkIngredientListModel = new DefaultListModel<>();
        JList<String> newDrinkIngredientList = new JList<>(newDrinkIngredientListModel);
        // current ingredients list
        JScrollPane scrollPane2 = new JScrollPane(newDrinkIngredientList);
        scrollPane2.setBounds(250, 190, 180, 250);
        frame.add(scrollPane2);
        // Add Ingredient button
        JButton addIngredientButton = new JButton("Add Ingredient");
        addIngredientButton.setBounds(50, 440, 180, 30);
        addIngredientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ingredientList.getSelectedValue() != null){
                    System.out.println(ingredientList.getSelectedValue());
                    newDrinkIngredientListModel.addElement(ingredientList.getSelectedValue());
                    allIngredientListModel.removeElement(ingredientList.getSelectedValue());
                }
            }
        });
        frame.add(addIngredientButton);
        // Remove Ingredient button
        JButton removeIngredientButton = new JButton("Remove Ingredient");
        removeIngredientButton.setBounds(250, 440, 180, 30);
        removeIngredientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(newDrinkIngredientList.getSelectedValue() != null){
                    System.out.println(newDrinkIngredientList.getSelectedValue());
                    allIngredientListModel.addElement(newDrinkIngredientList.getSelectedValue());
                    newDrinkIngredientListModel.removeElement(newDrinkIngredientList.getSelectedValue());

                }

            }
        });
        frame.add(removeIngredientButton);


        JLabel lblIngredientsList = new JLabel("Ingredients List:  ");
        lblIngredientsList.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIngredientsList.setBounds(50, 155, 137, 14);
        contentPane.add(lblIngredientsList);

        JLabel lblMenuItemIngredientsList = new JLabel("<html>New Menu Item<br>Ingredients List:</html>");
        lblMenuItemIngredientsList.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMenuItemIngredientsList.setVerticalAlignment(SwingConstants.NORTH);
        lblMenuItemIngredientsList.setBounds(250, 146, 137, 32);
        contentPane.add(lblMenuItemIngredientsList );

        JButton createItemButton = new JButton("Create Menu Item");
        createItemButton.setBounds(40, 490, 400, 45);
        createItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {      
                String itemName = itemNameTextField.getText();
                String itemType = choice.getSelectedItem();
                String itemPrice = priceTextField.getText();

                List<String> columnNames = new ArrayList<>();
                columnNames.add("name");
                List<List<String>> result = DatabaseHandler.query_SQL( 
                """
                    SELECT name FROM menu_item WHERE LOWER(name) = LOWER('%s');
                """.formatted(itemName,itemName), columnNames);

                
                if(itemName.equals("") || itemPrice.equals("")){
                    inputValidationLabel.setText("ERROR: One or more of the fields are empty.");
                    inputValidationLabel.setForeground(Color.red);
                }
                else if (newDrinkIngredientListModel.getSize() == 0){
                    inputValidationLabel.setForeground(Color.red);
                    inputValidationLabel.setText("ERROR: No ingredients inputted for item.".formatted(itemPrice));     
                }
                else{
                    try{
                        float floatValueItemPrice = Float.parseFloat(itemPrice);
                        if (!result.isEmpty()){
                            inputValidationLabel.setText("ERROR: Menu item already exist.");
                            inputValidationLabel.setForeground(Color.red);                        
                        }
                        else if (floatValueItemPrice < 0){
                            System.err.println("Invalid float format: " + itemPrice);
                            inputValidationLabel.setForeground(Color.red);
                            inputValidationLabel.setText("ERROR: %s is an invalid price.".formatted(itemPrice));                       
                        }
                        else{
                            //item successfully created
                            boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("topping", 
                            """
                            INSERT INTO menu_item (name, type, price)
                            VALUES ('%s','%s',%.2f);
                            """.formatted(itemName,itemType,floatValueItemPrice));
                            priceTextField.setText("");
                            itemNameLabel.setText("");
                            //TODO: add mapping of ingredients to add to menu_item_ingredient_map
                            //get all the ingredients for the new item
                            for (int i = 0; i < newDrinkIngredientListModel.getSize(); i++) {
                                String ingredient = newDrinkIngredientListModel.getElementAt(i);
                                Integer ingredient_Id = DatabaseHandler.ingredientNameIdMap.get(ingredient);
                                //Retrive menu_item_id of added item
                                columnNames.clear();
                                columnNames.add("menu_item_id");
                                List<List<String>> new_menu_item_id = DatabaseHandler.query_SQL(
                                """
                                SELECT menu_item_id
                                FROM menu_item
                                WHERE name = '%s';
                                """.formatted(itemName), 
                                columnNames);    
                                Integer menu_Item_Id = Integer.parseInt(new_menu_item_id.get(0).get(0));
                                ranSuccessfully = DatabaseHandler.run_SQL_Command("menu_ingredients_mapper", 
                                """
                                INSERT INTO menu_ingredients_mapper (menu_item_id , ingredients_id)
                                VALUES (%d,%d);
                                """.formatted(menu_Item_Id,ingredient_Id));
                                
                            }
                            newDrinkIngredientListModel.clear();

                            List<String> menuItem = new ArrayList<>();
                            menuItem.add("");
                            menuItem.add(itemName);
                            menuItem.add(itemType);
                            menuItem.add(itemPrice);
                            JButton menuItemButton = new JButton("<html>%s<br>Type: %s<br>Price: $%s</html>".formatted(menuItem.get(1),menuItem.get(2),menuItem.get(3))); 
                            menuItemButton.setPreferredSize(new Dimension(170, 70));
                            menuItemButton.setBackground(Color.pink);
                                menuItemButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        ManagerView.frameOpened.dispose();
                                        MenuItemFrame tempMenuItemFrame = new MenuItemFrame(menuItemButton, menuItem);
                                        ManagerView.frameOpened = tempMenuItemFrame.frame;
                                    }
                                });
                            ManagerView.menuItemPage.add(menuItemButton);
                            ManagerView.menuItemPage.revalidate();
                            ManagerView.menuItemPage.repaint();
                            inputValidationLabel.setText("SUCCESS: successfully added menu item.");
                            inputValidationLabel.setForeground(Color.green);
                        }
                    }catch (NumberFormatException e_2) {
                        System.err.println("Invalid float format: " + itemPrice);
                        inputValidationLabel.setForeground(Color.red);
                        inputValidationLabel.setText("ERROR: $%s is an invalid price.".formatted(itemPrice));
                    }
                }
            }
        });
        contentPane.add(createItemButton);
              
    }    
}
