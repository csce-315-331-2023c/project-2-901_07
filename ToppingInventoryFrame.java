import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * ToppingInventoryFrame is responsible for displaying a frame that
 * allows the user to view and modify the price and stock of toppings.
 * @author Quenten Hua
 */
public class ToppingInventoryFrame{

    public JFrame frame;
	public JPanel contentPanel;
    public JButton toppingButton;
    public List<String> topping;
    
    /**
     * Creates a JPanel for adjusting the price of a topping.
     * @return JPanel with components for price adjustment.
     */
    private JPanel changePricePanel(){
		JPanel changePricePanel = new JPanel();
        changePricePanel.setBounds(0, 0, 354, 160);
		changePricePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		changePricePanel.setLayout(null);
	    JLabel lblNewLabel = new JLabel("Current Price: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(34, 51, 126, 14);
		changePricePanel.add(lblNewLabel);
		
		JTextField currPriceTextField = new JTextField();
		currPriceTextField.setEditable(false);
		currPriceTextField.setBackground(new Color(192, 192, 192));
        currPriceTextField.setText("$" + topping.get(2));
        currPriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		currPriceTextField.setForeground(new Color(0, 0, 0));
		currPriceTextField.setBounds(170, 48, 86, 20);
		changePricePanel.add(currPriceTextField);
		currPriceTextField.setColumns(10);
		
		JTextField newPriceTextField = new JTextField();
		newPriceTextField.setColumns(10);
		newPriceTextField.setBounds(170, 79, 86, 20);
        newPriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		changePricePanel.add(newPriceTextField);
		
        JLabel lblNewLabel_1 = new JLabel(topping.get(1));
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
                // //System.out.println("input: \"%s\"".formatted(newPrice));
                if (newPrice.trim().isEmpty()){
                    validationLabel.setForeground(Color.red);
                    validationLabel.setText("ERROR: No price inputted.");
                }
                else{
                    try {
                        float floatValue = Float.parseFloat(newPrice);
                        if (floatValue < 0){
                            System.err.println("Invalid float format: " + newPrice);
                            validationLabel.setForeground(Color.red);
                            validationLabel.setText("ERROR: $%s is an invalid price.".formatted(newPrice));                            
                        }
                        else{
                            //System.out.println("Converted float value: " + floatValue);
                            boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("topping", 
                            """
                            UPDATE topping SET price = 
                            %s WHERE name = '%s';
                            """.formatted(newPrice,topping.get(1)));
                            if (ranSuccessfully == true){
                                currPriceTextField.setText("$%s".formatted(newPrice));
                                validationLabel.setForeground(Color.green);
                                validationLabel.setText("SUCCESS: price changed to $%s.".formatted(newPrice));
                                topping.set(2,newPrice);
                                toppingButton.setText("<html>%s<br>Price: $%s<br>Stock: %s</html>".formatted(topping.get(1),topping.get(2),topping.get(3))); 
                                DatabaseHandler.updateToppings();
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

    /**
     * Creates a JPanel for adjusting the stock of a topping.
     * @return JPanel with components for stock adjustment.
     */
    private JPanel changeStockPanel(){
		JPanel changePricePanel = new JPanel();
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
        currStockTextField.setText(topping.get(3));
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
		
        JLabel lblNewLabel_1 = new JLabel(topping.get(1));
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
                            UPDATE topping SET availability = 
                            %d WHERE name = '%s';
                            """.formatted(intValue,topping.get(1)));
                            if (ranSuccessfully == true){
                                currStockTextField.setText("%s".formatted(newStock));
                                validationLabel.setForeground(Color.green);
                                validationLabel.setText("SUCCESS: stock changed to %s.".formatted(newStock));
                                topping.set(3,newStock);
                                toppingButton.setText("<html>%s<br>Price: $%s<br>Stock: %s</html>".formatted(topping.get(1),topping.get(2),topping.get(3))); 
                                
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

	/**
	 * Constructor for the ToppingInventoryFrame.
	 * Initializes the frame and its components.
	 * 
	 * @param toppingButton the button representing the topping.
	 * @param topping the list containing topping details.
	 */
	public ToppingInventoryFrame(JButton toppingButton, List<String> topping) {
        this.frame = new JFrame();
        this.toppingButton = toppingButton;
        this.topping = topping;
		frame.setLayout(new BorderLayout(0, 0));
        frame.setVisible(true); // Display the new frame
        frame.setResizable(false);
		frame.setTitle("Topping");
		frame.setBounds(100, 100, 354, 250);
        frame.setLocationRelativeTo(null);
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(144, 44, 62));
        topPanel.setPreferredSize(new Dimension(354, 160));
        frame.add(topPanel, BorderLayout.NORTH);
        CardLayout contentPanelCardLayout = new CardLayout();
        contentPanel = new JPanel(contentPanelCardLayout);
        contentPanel.setPreferredSize(new Dimension(354,160));
        //inventory page panel
        JPanel changePricePanel = changePricePanel();
        JPanel changeStockPanel = changeStockPanel();

        // //Add different pages
        contentPanel.add(changePricePanel, "Price Page");
        contentPanel.add(changeStockPanel, "Stock Page");

        topPanel.add(contentPanel);
        //setContentPane(changePricePanel(topping));

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



        JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(240, 40, 240));
        bottomPanel.setBounds(0, 160, 338, 55);

		bottomPanel.setLayout(new GridLayout(1, 3, 0, 0));
        bottomPanel.add(adjustPrice_Button);
        bottomPanel.add(adjustStock_Button);
        frame.add(bottomPanel);
        /////////////////////////////////////////////////////////////////////
		
	}

}