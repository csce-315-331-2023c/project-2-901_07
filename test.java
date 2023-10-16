import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class test {
     test(){
        // Create a new JFrame
        JFrame frame = new JFrame("My First JFrame");

        // Set the default close operation to exit on close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a label with some text
        //JLabel label = new JLabel("Hello, World!");
        //label.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the label to the frame's content pane
        //frame.getContentPane().add(label);

        // Set the size of the frame
        frame.setSize(300, 200);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);

          JPanel salesReportPanel = new JPanel();
          salesReportPanel.setLayout(new GridLayout(0, 1));
          salesReportPanel.add(new JLabel("Sales Report Content")); 
          JPanel dataPickerPanel = new JPanel();
          dataPickerPanel.setLayout(new GridBagLayout());
          GridBagConstraints labelConstraints = new GridBagConstraints();
          labelConstraints.gridx = 0;
          labelConstraints.gridy = 0;
          labelConstraints.anchor = GridBagConstraints.WEST;


          Choice choice = new Choice();
          choice.add("date 1");

          Choice choice2 = new Choice();
          choice2.add("date 1");

          dataPickerPanel.add(new JLabel("Start Date : "),labelConstraints); 
          dataPickerPanel.add(choice);        
          dataPickerPanel.add(new JLabel("   End Date : ")); 
          dataPickerPanel.add(choice2);        
          salesReportPanel.add(dataPickerPanel);
          frame.add(salesReportPanel);


     }

     public static void main(String[] args){
          new test();
     }
}
