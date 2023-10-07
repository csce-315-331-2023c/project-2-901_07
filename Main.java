import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;

public class Main {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static JButton button1(){
        JButton button = new JButton();
        button.setBounds(200,100,100,50);
        return button;
    }
    public static JPanel bottomPanel(){
        //Bottom panel
        int panelHeight = screenSize.height / 6;
        int panelWidth = 0; // value does not matter
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.red);
        bottomPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        bottomPanel.add(button1());
        return bottomPanel;
    }

    public static JPanel rightPanel(){
        //Right panel
        int panelHeight = 0; // value does not matter
        int panelWidth = screenSize.width / 4;
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.blue);
        rightPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        return rightPanel;
    }

    public static JPanel centerPanel(){
        //Center panel
        int panelHeight = 0;
        int panelWidth = 0;
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.green);
        centerPanel.setPreferredSize(new Dimension(panelWidth,panelHeight));
        return centerPanel;
    }


    public static void main(String[] args){
        // FRAMES TUTORIAL

        JFrame frame = new JFrame();

        frame.setTitle("JFrame title goes here"); // sets title of frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
        //frame.setResizable(false); //prevent frame from being resized
        frame.setSize(screenSize.width, screenSize.height); //sets the x-dimension, and y-dimension of frame

        ImageIcon image = new ImageIcon("logo.png");
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(255,255,255));


        frame.setLayout(new BorderLayout());
        frame.add(rightPanel(),BorderLayout.EAST);
        frame.add(bottomPanel(),BorderLayout.SOUTH);
        frame.add(centerPanel(),BorderLayout.CENTER);

        frame.setVisible(true); //make frame visible
        // END OF FRAMES TUTORIAL





        // LABELS TUTORIAL
        JLabel label = new JLabel(); // create a label
        label.setText("Test"); // set text of label
        frame.add(label);

    }


}