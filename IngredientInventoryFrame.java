import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class IngredientInventoryFrame {
    public JFrame frame;
	public JPanel contentPanel;
    public JButton ingredientButton;
    public List<String> ingredient;

    private JPanel changeStockPanel(){
		JPanel changePricePanel = new JPanel();
		//changePricePanel.setBackground(new Color(50, 240, 240));
        changePricePanel.setBounds(0, 0, 354, 160);
		changePricePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		changePricePanel.setLayout(null);
	    JLabel lblNewLabel = new JLabel("Current Stock: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(34, 51, 126, 14);
		changePricePanel.add(lblNewLabel);
		
		JTextField currStockTextField = new JTextField();
		currStockTextField.setEditable(false);
		currStockTextField.setBackground(new Color(192, 192, 192));
        currStockTextField.setText(ingredient.get(2));
        currStockTextField.setHorizontalAlignment(SwingConstants.CENTER);
		currStockTextField.setForeground(new Color(0, 0, 0));
		currStockTextField.setBounds(170, 48, 86, 20);
		changePricePanel.add(currStockTextField);
		currStockTextField.setColumns(10);
		
		JTextField newPriceTextField = new JTextField();
		newPriceTextField.setColumns(10);
		newPriceTextField.setBounds(170, 79, 86, 20);
        newPriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		changePricePanel.add(newPriceTextField);
		
        JLabel lblNewLabel_1 = new JLabel(ingredient.get(1));
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
                //System.out.println("input: \"%s\"".formatted(newStock));
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
                            //System.out.println("Converted float value: " + intValue);
                            boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("topping", 
                            """
                            UPDATE ingredients SET availability = 
                            %d WHERE name = '%s';
                            """.formatted(intValue,ingredient.get(1)));
                            if (ranSuccessfully == true){
                                currStockTextField.setText("%s".formatted(newStock));
                                validationLabel.setForeground(Color.green);
                                validationLabel.setText("SUCCESS: stock changed to %s.".formatted(newStock));
                                ingredient.set(2,newStock);
                                ingredientButton.setText("<html>%s<br>Stock:%s</html>".formatted(ingredient.get(1),ingredient.get(2))); 
                                DatabaseHandler.updateIngredients();
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


    public IngredientInventoryFrame(JButton ingredientButton, List<String> ingredient) {
        this.frame = new JFrame();
        this.ingredientButton = ingredientButton;
        this.ingredient = ingredient;
        frame.setLayout(new BorderLayout(0, 0));
        frame.setVisible(true); // Display the new frame
        frame.setResizable(false);
		frame.setTitle("Ingredient");
		frame.setBounds(100, 100, 354, 250);
        frame.setLocationRelativeTo(null);
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(144, 44, 62));
        topPanel.setPreferredSize(new Dimension(354, 160));
        frame.add(topPanel, BorderLayout.NORTH);
        CardLayout contentPanelCardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(contentPanelCardLayout);
        contentPanel.setPreferredSize(new Dimension(354,160));
        //inventory page panel

        JPanel changeStockPanelIngredient = changeStockPanel();
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
        // JButton deleteItem_Button = new JButton("Delete Item");
        // deleteItem_Button.setForeground(Color.white);
        // deleteItem_Button.setBackground(Color.red);
		// deleteItem_Button.addActionListener(new ActionListener() {
		// 	public void actionPerformed(ActionEvent e) {
        //         boolean ranSuccessfully = run_SQL_Command("topping", 
        //         """
        //         DELETE FROM ingredient
        //         WHERE name = '%s';
        //         """.formatted(ingredient.get(1)));
        //     }
        // });
     


        JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(240, 40, 240));
        bottomPanel.setBounds(0, 160, 338, 55);

		bottomPanel.setLayout(new GridLayout(1, 3, 0, 0));
        bottomPanel.add(adjustStock_Button);
        //bottomPanel.add(deleteItem_Button);
        frame.add(bottomPanel);
    
        /////////////////////////////////////////////////////////////////////
        

    }
    
}
