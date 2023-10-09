import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CategoryHandler {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static void categoryHandlerPanel(Component parent) {
        // Create a JDialog for the popup panel
        JDialog popupDialog = new JDialog();
        popupDialog.setTitle("Drink Customization Panel");
        popupDialog.setSize(screenSize.width, screenSize.height);
        popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create a JPanel for the content of the popup
        JPanel popupPanel = new JPanel(new GridLayout(3, 0, 20, 20));
        popupPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Section for ice level
        JPanel icePanel = new JPanel(new GridLayout(1, 5, 20, 20));
        JLabel iceLabel = new JLabel("Ice Level:");
        icePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        icePanel.add(iceLabel);
        String[] iceOptions = {"0%", "25%", "50%", "100%"};
        for (String option : iceOptions) {
            JButton iceButton = new JButton(option);
            icePanel.add(iceButton);
        }
        popupPanel.add(icePanel);

        // Section for sugar level
        JPanel sugarPanel = new JPanel(new GridLayout(1, 5, 20, 20));
        JLabel sugarLabel = new JLabel("Sugar Level:");
        sugarPanel.add(sugarLabel);
        sugarPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        String[] sugarOptions = {"0%", "25%", "50%", "100%"};
        for (String option : sugarOptions) {
            JButton sugarButton = new JButton(option);
            sugarPanel.add(sugarButton);
        }
        popupPanel.add(sugarPanel);

        // Section for toppings
        JPanel toppingsPanel = new JPanel(new GridLayout(0, 5, 20, 20));
        JLabel toppingsLabel = new JLabel("Toppings:");
        toppingsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        toppingsPanel.add(toppingsLabel);
        for (int i = 1; i <= 50; i++) {
            JButton toppingButton = new JButton("Topping " + i);
            toppingsPanel.add(toppingButton);
        }
        
        popupPanel.add(new JScrollPane(toppingsPanel));

        popupDialog.add(popupPanel);

        popupDialog.setLocationRelativeTo(parent);

        popupDialog.setVisible(true);

    }
}

