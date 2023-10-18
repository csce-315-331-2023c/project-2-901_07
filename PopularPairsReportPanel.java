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

/**
 * Provides a panel to generate and display reports about popular pairings of items 
 * within a specified date range.
 * 
 * @author Alexandra Saxton
 */
public class PopularPairsReportPanel{
    public JPanel popularPairsReportPanel;
    private JPanel resultRow;

    /**
     * Constructs a new PopularPairsReportPanel.
     */
    public PopularPairsReportPanel(){
        this.popularPairsReportPanel = new JPanel();
        popularPairsReportPanel.setLayout(new GridBagLayout());

        // create elements in the header row
        JPanel dateEntryRow = new JPanel();
        JLabel startDateLabel = new JLabel("Start Date:");
        JTextField startDateMonthField = new JTextField("MM",3);
        startDateMonthField.setMargin(new Insets(5, 5, 5, 5));
        JTextField startDateDayField = new JTextField("DD",3);
        startDateDayField.setMargin(new Insets(5, 5, 5, 5));
        JTextField startDateYearField = new JTextField("YYYY",4);
        startDateYearField.setMargin(new Insets(5, 5, 5, 5));
        JLabel endDateLabel = new JLabel("End Date:");
        JTextField endDateMonthField = new JTextField("MM",3);
        endDateMonthField.setMargin(new Insets(5, 5, 5, 5));
        JTextField endDateDayField = new JTextField("DD",3);
        endDateDayField.setMargin(new Insets(5, 5, 5, 5));
        JTextField endDateYearField = new JTextField("YYYY",4);
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
                try{
                    int startMonth = Integer.parseInt(startDateMonth);
                    int startDay = Integer.parseInt(startDateDay);
                    int startYear = Integer.parseInt(startDateYear);
                    int endMonth = Integer.parseInt(endDateMonth);
                    int endDay = Integer.parseInt(endDateDay);
                    int endYear = Integer.parseInt(endDateYear);

                    // check bounds
                    if(startMonth < 1 || startMonth > 12 ||
                       startDay < 1 || startDay > 31 || // we live in a society where all months are 31 days
                       startYear < 2000 || endYear > 3000 ||
                       endMonth < 1 || endMonth > 12 ||
                       endDay < 1 || endDay > 31 ||
                       endYear < 2000 || endYear > 3000){
                        updateReportPanel("Invalid input. Please enter a valid date.");
                        return;
                       }
                    
                    // check if date 2 is greater than date 1
                    if (startYear > endYear){
                        updateReportPanel("Invalid input. Make sure the start date preceeds the end date.");
                        return;
                    }
                    if (startYear == endYear && startMonth > endMonth){
                        updateReportPanel("Invalid input. Make sure the start date preceeds the end date.");
                        return;
                    }
                    if(startYear == endYear && startMonth == endMonth && startDay > endDay){
                        updateReportPanel("Invalid input. Make sure the start date preceeds the end date.");
                        return;
                    }

                }
                catch(NumberFormatException except){


                    updateReportPanel("Invalid input. Please enter numerical values for the date.");
                    return;

                }
                
                List<List<String>> result = getPopularPairs(startDateMonth, startDateDay, startDateYear,
                                    endDateMonth, endDateDay, endDateYear);

                if(result.size()==0){
                    updateReportPanel("No orders were found in this date range.");
                    return;
                }

                updateReportPanel(result);
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
        updateReportPanel("Please enter date range to view popular item pairings.");

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

        popularPairsReportPanel.add(dateEntryRow, dateEntryRowConstraints);
        popularPairsReportPanel.add(resultRow, resultRowConstraints);
    }

    

    /**
     * Fetches popular pairs of items from the database based on orders between the specified dates.
     * 
     * @param startMonth Start date's month.
     * @param startDay Start date's day.
     * @param startYear Start date's year.
     * @param endMonth End date's month.
     * @param endDay End date's day.
     * @param endYear End date's year.
     * @return A list of popular pairs.
     */
    public List<List<String>> getPopularPairs(String startMonth, String startDay, String startYear,
                                            String endMonth, String endDay, String endYear){

        String start = startYear+"-"+startMonth+"-"+startDay;
        String end = endYear+"-"+endMonth+"-"+endDay;
        String sql_cmd = String.format(
        """
            WITH PairedDrinks AS (
            SELECT
                d1.order_id,
                d1.menu_item_id AS menu_item_id1,
                d2.menu_item_id AS menu_item_id2
            FROM drink d1
            JOIN drink d2 ON d1.order_id = d2.order_id AND d1.menu_item_id < d2.menu_item_id
            JOIN orders o ON d1.order_id = o.order_id
            WHERE o.date BETWEEN '%s' AND '%s'
            )

            SELECT
                p.menu_item_id1,
                mi1.name AS menu_item_name1,
                p.menu_item_id2,
                mi2.name AS menu_item_name2,
                COUNT(*) AS frequency
            FROM PairedDrinks p
            JOIN menu_item mi1 ON p.menu_item_id1 = mi1.menu_item_id
            JOIN menu_item mi2 ON p.menu_item_id2 = mi2.menu_item_id
            GROUP BY p.menu_item_id1, mi1.name, p.menu_item_id2, mi2.name
            ORDER BY frequency DESC;
        """, start, end);
        List<String> columnNames = new ArrayList<>();
        columnNames.add("menu_item_name1");
        columnNames.add("menu_item_name2");
        columnNames.add("frequency");

        return DatabaseHandler.query_SQL(sql_cmd, columnNames);
        
    }

    /**
     * Updates the report panel to display the given popular pairs.
     * 
     * @param popularPairs A list of popular pairs to display.
     */
    public void updateReportPanel(List<List<String>> popularPairs) {
        // Remove all previous components from the resultRow
        resultRow.removeAll();
        resultRow.setLayout(new BorderLayout());

        // Create column names for the table
        String[] columnNames = {"Frequency", "Item 1", "Item 2"};
        
        // Convert popularPairs list into a 2D array for JTable
        String[][] data = new String[popularPairs.size()][3];
        for (int i = 0; i < popularPairs.size(); i++) {
            List<String> row = popularPairs.get(i);
            data[i][0] = row.get(2); // Frequency
            data[i][1] = row.get(0); // Item 1
            data[i][2] = row.get(1); // Item 2
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

    /**
     * Updates the report panel to display the given message.
     * 
     * @param message The message to display.
     */
    public void updateReportPanel(String message){
        resultRow.removeAll();
        resultRow.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Calibri", Font.BOLD, 32));
        resultRow.add(messageLabel);
        resultRow.repaint();
        resultRow.revalidate();
    }

    /**
     * Generates a JTextField with specified default text and maximum length.
     *
     * @param defaultText The default text to display in the text field.
     * @param maxLength   The maximum length of text allowed in the text field.
     * @return JTextField with specified default text and length.
     */
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

    /**
     * Returns the main JPanel for this report panel.
     * 
     * @return The main JPanel.
     */
    public JPanel getPopularPairsReportPanel(){
        return this.popularPairsReportPanel;
    }

}