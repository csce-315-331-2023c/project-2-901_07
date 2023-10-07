import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class GUI implements ActionListener {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JButton checkoutButton;
    JButton switchViewButton;
    JLabel currentViewLabel;
    String current_view = "Cashier";

    public JButton checkout_button() {
        checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(200, 100, 100, 50);
        checkoutButton.addActionListener(this);
        EmptyBorder margin = new EmptyBorder(200, 50, 200, 50);
        checkoutButton.setBorder(margin);
        return checkoutButton;
    }

    public JButton switchView_button() {
        switchViewButton = new JButton("Switch View");
        switchViewButton.setBounds(200, 100, 100, 50);
        switchViewButton.addActionListener(this);
        return switchViewButton;
    }

    public JPanel bottomPanel() {
        // Bottom panel
        int panelHeight = screenSize.height / 6;
        int panelWidth = 0; // value does not matter
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 500, 10));
        bottomPanel.setBackground(Color.red);
        bottomPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        JLabel emptyLabel = new JLabel("test");

        bottomPanel.add(checkout_button());
        bottomPanel.add(emptyLabel);

        //empty space for grid

        return bottomPanel;
    }

    public JPanel rightPanel() {
        // Right panel
        int panelHeight = 0; // value does not matter
        int panelWidth = screenSize.width / 4;
        JPanel rightPanel = new JPanel(new GridLayout(5, 1, 500, 10));
        rightPanel.setBackground(Color.blue);
        rightPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        currentViewLabel = new JLabel("Current View: " + current_view);
        rightPanel.add(currentViewLabel);
        rightPanel.add(switchView_button());
        return rightPanel;

    }

    public JPanel centerPanel() {
        // Center panel
        int panelHeight = 300;
        int panelWidth = screenSize.width - (screenSize.width / 4);
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.green);
        centerPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        return centerPanel;
    }

    public GUI() {
        // FRAMES TUTORIAL
        JFrame frame = new JFrame();

        frame.setTitle("JFrame title goes here"); // sets title of frame
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
