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
 * Provides a panel to visualize excess ingredients based on a specified date and time.
 * Users input a date and time, and the panel displays ingredients with more than 90%
 * original inventory remaining since the given date and time.
 * 
 * @author Alexandra Saxton
 */
public class ExcessReportPanel{
    public JPanel excessReportPanel;
    private JPanel resultRow;

    /**
     * Constructs an ExcessReportPanel.
     */
    public ExcessReportPanel(){
        this.excessReportPanel = new JPanel();
        excessReportPanel.setLayout(new GridBagLayout());

        // create elements in the header row
        JPanel dateEntryRow = new JPanel();

        JLabel dateLabel = new JLabel("Date:");
        JTextField monthField = getTextFieldWithText("MM",3);
        monthField.setMargin(new Insets(5, 5, 5, 5));
        JTextField dayField = getTextFieldWithText("DD",3);
        dayField.setMargin(new Insets(5, 5, 5, 5));
        JTextField yearField = getTextFieldWithText("YYYY",4);
        yearField.setMargin(new Insets(5, 5, 5, 5));

        JLabel timeLabel = new JLabel("Time:");
        JTextField hourField = getTextFieldWithText("hh",3);
        hourField.setMargin(new Insets(5, 5, 5, 5));
        JTextField minuteField = getTextFieldWithText("mm",3);
        minuteField.setMargin(new Insets(5, 5, 5, 5));
        JTextField secondField = getTextFieldWithText("ss",3);
        secondField.setMargin(new Insets(5, 5, 5, 5));

        JButton submitButton = new JButton("Submit");

        // Increase the font of all elements 
        Font font = new Font("Calibri", Font.BOLD, 24);
        dateEntryRow.setFont(font);
        dateLabel.setFont(font);
        monthField.setFont(font);
        dayField.setFont(font);
        yearField.setFont(font);
        timeLabel.setFont(font);
        hourField.setFont(font);
        minuteField.setFont(font);
        secondField.setFont(font);
        submitButton.setFont(font);



        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // validate user input is correct
                String monthText = monthField.getText();
                String dayText = dayField.getText();
                String yearText = yearField.getText();
                String hourText = hourField.getText();
                String minuteText = minuteField.getText();
                String secondText = secondField.getText();
               
                
                try{
                    int month = Integer.parseInt(monthText);
                    int day = Integer.parseInt(dayText);
                    int year = Integer.parseInt(yearText);
                    int hour = Integer.parseInt(hourText);
                    int minute = Integer.parseInt(minuteText);
                    int second = Integer.parseInt(secondText);
                   

                    // check bounds
                    if(month < 1 || month > 12 ||
                       day < 1 || day > 31 || // we live in a society where all months are 31 days
                       year < 2000 || year > 3000 ||
                       hour < 0 || hour > 23 ||
                       minute < 0 || minute > 59 ||
                       second < 0 || second > 59){
                        updateReportPanel("Invalid input. Please enter a valid time and date.");
                        return;
                       }

                }
                catch(NumberFormatException except){
                    updateReportPanel("Invalid input. Please enter numerical values for the time and date.");
                    return;
                }
                
                List<List<String>> result = getExcessReport(monthText, dayText, yearText, hourText, minuteText, secondText);

                if(result.size()==0){
                    updateReportPanel("There are no excess ingredients using the given date.");
                    return;
                }

                updateReportPanel(result);
            }
        });

        dateEntryRow.add(dateLabel);
        dateEntryRow.add(monthField);
        dateEntryRow.add(dayField);
        dateEntryRow.add(yearField);
        dateEntryRow.add(timeLabel);
        dateEntryRow.add(hourField);
        dateEntryRow.add(minuteField);
        dateEntryRow.add(secondField);

        dateEntryRow.add(submitButton);

        resultRow = new JPanel();
        resultRow.setLayout(new BorderLayout());
        updateReportPanel("<html>Please enter a date and time to view ingredients<br>with more than 90% original inventory remaining.</html>");

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

        excessReportPanel.add(dateEntryRow, dateEntryRowConstraints);
        excessReportPanel.add(resultRow, resultRowConstraints);
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
     * Fetches a report of excess ingredients based on the provided date and time.
     *
     * @param month   The month part of the date.
     * @param day     The day part of the date.
     * @param year    The year part of the date.
     * @param hour    The hour part of the time.
     * @param minute  The minute part of the time.
     * @param second  The second part of the time.
     * @return A list containing the report data.
     */
    public List<List<String>> getExcessReport(String month, String day, String year, String hour, String minute, String second){

        String date = year+"-"+month+"-"+day;
        String time = hour+":"+minute+":"+second;

        String sql_cmd = String.format(
        """
        WITH IngredientUsage AS (
            WITH FilteredOrders AS (
                SELECT order_id
                FROM orders
                WHERE (date, time) > ('%s', '%s') -- Replace USER_INPUT_DATE and USER_INPUT_TIME with actual inputs
            )
            , IngredientUsage AS (
                SELECT mim.ingredients_id
                FROM FilteredOrders fo
                JOIN drink d ON fo.order_id = d.order_id
                JOIN menu_ingredients_mapper mim ON d.menu_item_id = mim.menu_item_id
            )
            SELECT i.ingredients_id as IngredientID, i.name AS IngredientName, COALESCE(COUNT(iu.ingredients_id), 0) AS TimesUsed
            FROM ingredients i
            LEFT JOIN IngredientUsage iu ON i.ingredients_id = iu.ingredients_id
            GROUP BY i.ingredients_id, i.name
            ORDER BY TimesUsed DESC, IngredientName
        )
        SELECT
            IngredientID,
            IngredientName,
            TimesUsed
        FROM IngredientUsage iu
        JOIN ingredients i ON iu.IngredientID = i.ingredients_id
        WHERE (1.0 * iu.TimesUsed) / (iu.TimesUsed + i.availability) < 0.1
        ORDER BY iu.TimesUsed DESC;
        """, date, time);
        
        List<String> columnNames = new ArrayList<>();
        columnNames.add("IngredientID");
        columnNames.add("IngredientName");
        columnNames.add("TimesUsed");

        return DatabaseHandler.query_SQL(sql_cmd, columnNames);

        
    }

    /**
     * Updates the report panel with the given table data.
     *
     * @param tableData The data to be displayed in the table format.
     */
    public void updateReportPanel(List<List<String>> tableData) {
        // Remove all previous components from the resultRow
        resultRow.removeAll();
        resultRow.setLayout(new BorderLayout());

        // Create column names for the table
        String[] columnNames = {"Ingredient", "Number Used"};
        
        // Convert popularPairs list into a 2D array for JTable
        String[][] data = new String[tableData.size()][3];
        for (int i = 0; i < tableData.size(); i++) {
            List<String> row = tableData.get(i);
            data[i][0] = row.get(1); // Frequency
            data[i][1] = row.get(2); // Item 1
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
     * Updates the report panel with the given message.
     *
     * @param message The message to be displayed.
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
     * Returns the excess report panel.
     *
     * @return The excess report panel.
     */
    public JPanel getExcessReportPanel(){
        return this.excessReportPanel;
    }

}