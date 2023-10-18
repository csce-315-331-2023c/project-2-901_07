import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Represents a frame for displaying and modifying the attributes of a menu item.
 * The frame allows the user to view the current price of the item and update the price.
 * Any updates to the price are saved to the database and reflected immediately in the UI.
 * <p>
 * The frame is divided into a top and bottom panel. The top panel displays the current price
 * and provides an interface for changing the price. The bottom panel contains buttons for
 * various operations, currently only a "Price" button to show the price change interface.
 * </p>
 * 
 * @author Quenten Hua
 */
public class MenuItemFrame {

    public JFrame frame;
	public JPanel contentPanel;
    public JButton menuItemButton;
    public List<String> menuItem;

    /**
     * Constructs a panel for changing the price of the menu item.
     *
     * @return JPanel containing the price change interface.
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
        currPriceTextField.setText("$" + menuItem.get(3));
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
		
        JLabel lblNewLabel_1 = new JLabel(menuItem.get(1));
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
                            boolean ranSuccessfully = DatabaseHandler.run_SQL_Command("topping", 
                            """
                            UPDATE menu_item SET price = 
                            %s WHERE name = '%s';
                            """.formatted(newPrice,menuItem.get(1)));
                            if (ranSuccessfully == true){
                                currPriceTextField.setText("$%s".formatted(newPrice));
                                validationLabel.setForeground(Color.green);
                                validationLabel.setText("SUCCESS: price changed to $%s.".formatted(newPrice));
                                menuItem.set(3,newPrice);
                                menuItemButton.setText("<html>%s<br>Type: %s<br>Price: $%s</html>".formatted(menuItem.get(1),menuItem.get(2),menuItem.get(3))); 
                                
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
     * Initializes a new MenuItemFrame.
     * <p>
     * Creates a frame with a top panel that displays and allows editing of
     * the current price of the menu item. The bottom panel contains buttons for
     * different operations. The "Price" button displays the price change interface.
     * </p>
     * 
     * @param menuItemButton The button representing the menu item in the main UI.
     * @param menuItem       A list representing the attributes of the menu item. Expected format:
     *                       [id, name, type, price].
     */
    public MenuItemFrame(JButton menuItemButton, List<String> menuItem) {
        this.frame = new JFrame();
        this.menuItemButton = menuItemButton;
        this.menuItem = menuItem;
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

        // //Add different pages
        contentPanel.add(changePricePanel, "Price Page");

        topPanel.add(contentPanel);

        ///////BOTTOM PANEL COMPONENTS//////////////

        //adjustPrice_button
        JButton adjustPrice_Button = new JButton("Price");
		adjustPrice_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                contentPanelCardLayout.show(contentPanel, "Price Page");
            }
        });

        JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(240, 40, 240));
        bottomPanel.setBounds(0, 160, 338, 55);

		bottomPanel.setLayout(new GridLayout(1, 3, 0, 0));
        bottomPanel.add(adjustPrice_Button);
        frame.add(bottomPanel);
        /////////////////////////////////////////////////////////////////////
		
	}
}
