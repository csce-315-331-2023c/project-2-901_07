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
 * Represents a sales report panel.
 * 
 * @author Quenten Hua
 */
public class SalesReportPanel{
    public JPanel salesReportPanel;
    private JPanel resultRow;

    /**
     * Initializes a new instance of the SalesReportPanel.
     */
    public SalesReportPanel(){
        this.salesReportPanel = new JPanel();
        salesReportPanel.setLayout(new GridBagLayout());

        // create elements in the header row
        JPanel dateEntryRow = new JPanel();
        JLabel startDateLabel = new JLabel("Start Date:");
        /////
        JTextField startDateMonthField = getTextFieldWithText("MM",3);
        startDateMonthField.setMargin(new Insets(5, 5, 5, 5));

        

        /////
        JTextField startDateDayField = getTextFieldWithText("DD",3);
        startDateDayField.setMargin(new Insets(5, 5, 5, 5));

        /////
        JTextField startDateYearField = getTextFieldWithText("YYYY", 4);
        startDateYearField.setMargin(new Insets(5, 5, 5, 5));

        JLabel endDateLabel = new JLabel("End Date:");
        JTextField endDateMonthField = getTextFieldWithText("MM",3);
        endDateMonthField.setMargin(new Insets(5, 5, 5, 5));
        JTextField endDateDayField = getTextFieldWithText("DD",3);
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
        updateReportPanel("Please enter a date range to view the sales report.");

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

    /**
     * Creates and returns a JTextField initialized with the given text and maximum length.
     *
     * @param defaultText The default text to display in the JTextField.
     * @param maxLength The maximum number of characters the JTextField can accept.
     * @return A JTextField object initialized with the given parameters.
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
     * Retrieves and updates the sales report based on the given start and end date.
     *
     * @param startMonth The starting month.
     * @param startDay The starting day.
     * @param startYear The starting year.
     * @param endMonth The ending month.
     * @param endDay The ending day.
     * @param endYear The ending year.
     */
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

     /**
     * Updates the result row with the sales report data.
     *
     * @param salesReport A list containing rows of sales report data.
     */
    public void updateReportPanel(List<List<String>> salesReport) {
        // Remove all previous components from the resultRow
        resultRow.removeAll();
        resultRow.setLayout(new BorderLayout());

        // Create column names for the table
        String[] columnNames = {"Name", "Order Count"};
        
        // Convert salesReport list into a 2D array for JTable
        String[][] data = new String[salesReport.size()][3];
        for (int i = 0; i < salesReport.size(); i++) {
            List<String> row = salesReport.get(i);
            data[i][0] = row.get(0); // Name
            data[i][1] = row.get(1); // Order Count
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
     * Updates the result row with a given message.
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
     * Returns the sales report panel.
     *
     * @return The sales report panel.
     */
    public JPanel getSalesReportPanel(){
        return this.salesReportPanel;
    }

}