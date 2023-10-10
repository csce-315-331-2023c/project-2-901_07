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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerView {
    // Class contains all methods associated with the pages for the Manager view
    static JFrame frameOpened = new JFrame(null, null);

    static JFrame openNewFrame(List<String> topping) {
		JFrame changePriceFrame = new JFrame();
        changePriceFrame.setVisible(true); // Display the new frame
        changePriceFrame.setResizable(false);
		changePriceFrame.setTitle("Change Price");
		changePriceFrame.setBounds(100, 100, 354, 199);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		changePriceFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Current Price: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(34, 51, 126, 14);
		contentPane.add(lblNewLabel);
		
		JTextField textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(new Color(192, 192, 192));
        textField.setText("$" + topping.get(1));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(new Color(0, 0, 0));
		textField.setBounds(170, 48, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JTextField textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(170, 79, 86, 20);
        textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(textField_1);
		
		JButton btnNewButton = new JButton("Change Price");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(75, 107, 181, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel(topping.get(0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 11, 318, 31);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewPrice = new JLabel("New Price: ");
		lblNewPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPrice.setBounds(34, 82, 126, 14);
		contentPane.add(lblNewPrice);
        changePriceFrame.setLocationRelativeTo(null);
        return changePriceFrame;
 
 
    }

    public static JPanel inventoryPage(){
        //Method contains commands to format inventory page
        JPanel inventoryPage = new JPanel(new GridLayout(4, 5, 30, 30));
        inventoryPage.setBorder(new EmptyBorder(40, 40, 40, 40));
        inventoryPage.setBackground(Color.red);
        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("price");
        List<List<String>> toppings = query("topping", columnNames);
        for (List<String> topping : toppings) {
          JButton toppingButton = new JButton( "<html>%s<br>Price:$%s</html>".formatted(topping.get(0),topping.get(1)));
          toppingButton.setBackground(Color.gray);
            toppingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(topping.get(0));
                    frameOpened.dispose();
                    toppingButton.setBackground(Color.gray);
                    inventoryPage.add(toppingButton);
                    frameOpened = openNewFrame(topping);
                }
            });
          inventoryPage.add(toppingButton);
        }
        return inventoryPage;
        
    }

    public static JPanel transactionPage(){
        //Method contains commands to format inventory page
        JPanel inventoryPage = new JPanel(new GridLayout(4, 5, 30, 30));
        inventoryPage.setBorder(new EmptyBorder(40, 40, 40, 40));
        inventoryPage.setBackground(Color.red);
        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("price");
        List<List<String>> toppings = query("topping", columnNames);
        for (List<String> topping : toppings) {
            frameOpened.dispose();
            JButton toppingButton = new JButton( "<html>%s<br>Price: $%s</html>".formatted(topping.get(0),topping.get(1)));
            toppingButton.setBackground(Color.gray);
            inventoryPage.add(toppingButton);
            frameOpened = openNewFrame(topping);
        }
        return inventoryPage;
        
    }
    
    public static List<List<String>> query(String tableName, List<String> columnNames){
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
            String selectTable = "SELECT * FROM "+tableName+";";
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

