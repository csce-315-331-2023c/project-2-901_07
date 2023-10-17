import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.event.*;
import javax.swing.border.*;

public class SalesReportPanel{
    public JPanel salesReportPanel;
    private JPanel resultRow;

    public SalesReportPanel(){
        this.salesReportPanel = new JPanel();
        salesReportPanel.setLayout(new GridBagLayout());

        // create elements in the header row
        JPanel dateEntryRow = new JPanel();
        JLabel startDateLabel = new JLabel("Start Date:");
        /////
        JTextField startDateMonthField = getTextFieldWithText("MM",3);
        startDateMonthField.setMargin(new Insets(5, 5, 5, 5));
        //Choice startDateMonthField = getChoiceMonth();
        

        /////
        JTextField startDateDayField = getTextFieldWithText("DD",3);
        startDateDayField.setMargin(new Insets(5, 5, 5, 5));
        //Choice startDateDayField = getChoiceDay();
        /////
        JTextField startDateYearField = getTextFieldWithText("YYYY", 4);
        startDateYearField.setMargin(new Insets(5, 5, 5, 5));
        //Choice startDateYearField = getChoiceYear();

        JLabel endDateLabel = new JLabel("End Date:");
        JTextField endDateMonthField = getTextFieldWithText("MM",2);
        endDateMonthField.setMargin(new Insets(5, 5, 5, 5));
        JTextField endDateDayField = getTextFieldWithText("DD",2);
        endDateDayField.setMargin(new Insets(5, 5, 5, 5));
        JTextField endDateYearField = getTextFieldWithText("YYYY",4);
        endDateYearField.setMargin(new Insets(5, 5, 5, 5));
        JButton submitButton = new JButton("Submit");

        // Increase the font of all elements 
        Font font = new Font("Calibri", Font.BOLD, 24);
        dateEntryRow.setFont(font);
        startDateLabel.setFont(font);
        startDateMonthField.setFont(font);
        startDateDayField.setFont(font);
        startDateYearField.setFont(font);
        endDateLabel.setFont(font);
        endDateMonthField.setFont(font);
        endDateDayField.setFont(font);
        endDateYearField.setFont(font);
        submitButton.setFont(font);



        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // validate user input is correct
                String startDateMonth = startDateMonthField.getText();
                String startDateDay = startDateDayField.getText();
                String startDateYear = startDateYearField.getText();
                String endDateMonth = endDateMonthField.getText();
                String endDateDay = endDateDayField.getText();
                String endDateYear = endDateYearField.getText();
                // valiate user input
                // ?if (startDateMonthField.getText() == "MM")
                try{
                    Integer.parseInt(startDateMonth);
                    Integer.parseInt(startDateDay);
                    Integer.parseInt(startDateYear);
                    Integer.parseInt(endDateMonth);
                    Integer.parseInt(endDateDay);
                    Integer.parseInt(endDateYear);
                }
                catch(NumberFormatException except){
                    // do something
                    System.out.println("user input error for date");
                }
                getSalesReport(startDateMonth, startDateDay, startDateYear,
                                    endDateMonth, endDateDay, endDateYear);
            }
        });

        dateEntryRow.add(startDateLabel);
        dateEntryRow.add(startDateMonthField);
        dateEntryRow.add(startDateDayField);
        dateEntryRow.add(startDateYearField);
        
        dateEntryRow.add(endDateLabel);
        dateEntryRow.add(endDateMonthField);
        dateEntryRow.add(endDateDayField);
        dateEntryRow.add(endDateYearField);

        dateEntryRow.add(submitButton);

        resultRow = new JPanel();
        resultRow.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        resultRow.add(new JScrollPane(textArea));

        // Create GridBagConstraints for dateEntryRow and resultRow
        GridBagConstraints dateEntryRowConstraints = new GridBagConstraints();
        dateEntryRowConstraints.gridx = 0;
        dateEntryRowConstraints.gridy = 0;
        dateEntryRowConstraints.weighty = 0;  // Top row should not expand vertically
        dateEntryRowConstraints.fill = GridBagConstraints.HORIZONTAL;

        GridBagConstraints resultRowConstraints = new GridBagConstraints();
        resultRowConstraints.gridx = 0;
        resultRowConstraints.gridy = 1;
        resultRowConstraints.weighty = 1;  // Bottom row should expand vertically
        resultRowConstraints.weightx = 1;
        resultRowConstraints.fill = GridBagConstraints.BOTH;

        salesReportPanel.add(dateEntryRow, dateEntryRowConstraints);
        salesReportPanel.add(resultRow, resultRowConstraints);
    }

    public JTextField getTextFieldWithText(String defaultText, int maxLength){
        JTextField textField = new JTextField(maxLength);
        textField.setText(defaultText);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(defaultText)) {
                    textField.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(defaultText);
                }
            }
        });
        return textField;
    }

    public Choice getChoiceMonth(){
        Choice choice = new Choice();
        choice.add("MM"); // This will be the default selection
        choice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                System.out.println("new month selected");
            }
        });
        return choice;
    }   

    public Choice getChoiceDay(){
        Choice choice = new Choice();
        choice.add("DD"); // This will be the default selection
        choice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                System.out.println("new day selected");
            }
        });
        return choice;
    }   

    public Choice getChoiceYear(){
        Choice choice = new Choice();
        choice.add("YEAR"); // This will be the default selection
        choice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                System.out.println("new year selected");
            }
        });
        return choice;
    }  

    public void getSalesReport(String startMonth, String startDay, String startYear,
                                            String endMonth, String endDay, String endYear){

        String start = startYear+"-"+startMonth+"-"+startDay;
        String end = endYear+"-"+endMonth+"-"+endDay;
        String sql_cmd = String.format(
        """
            WITH DrinksInRange AS (
            SELECT d.menu_item_id
            FROM orders o
            JOIN drink d ON o.order_id = d.order_id
            WHERE o.date BETWEEN '%s' AND '%s'  -- Replace START and END with the actual dates
        )

            SELECT mi.name, COUNT(dri.menu_item_id) as order_count
            FROM DrinksInRange dri
            JOIN menu_item mi ON dri.menu_item_id = mi.menu_item_id
            GROUP BY mi.name
            ORDER BY order_count DESC;
        """, start, end);
        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("order_count");
        List<List<String>> result = DatabaseHandler.query_SQL(sql_cmd, columnNames);
        updateReportPanel(result);
        
    }

    // public JPanel resulingSalesReport(List )

    public void updateReportPanel(List<List<String>> salesReport) {
        // Remove all previous components from the resultRow
        resultRow.removeAll();

        // Create column names for the table
        String[] columnNames = {"Name", "Order Count"};
        
        // Convert salesReport list into a 2D array for JTable
        String[][] data = new String[salesReport.size()][3];
        for (int i = 0; i < salesReport.size(); i++) {
            List<String> row = salesReport.get(i);
            data[i][0] = row.get(0); // Name
            data[i][1] = row.get(1); // Order Count
            System.out.println(row);
        }
        
        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Calibri", Font.BOLD, 16));
        int fontHeight = table.getFontMetrics(table.getFont()).getHeight();
        table.setRowHeight(fontHeight + 6);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(500, 300)); 

        resultRow.add(tableScrollPane, BorderLayout.CENTER);
        resultRow.repaint();
        resultRow.revalidate();
    }

    public JPanel getSalesReportPanel(){
        return this.salesReportPanel;
    }

}