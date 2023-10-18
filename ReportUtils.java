public  class ReportUtils{

    
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
     * Creates a text field with a default text and a maximum length.
     * 
     * @param defaultText Default text to display in the text field.
     * @param maxLength Maximum length for the text field.
     * @return A new JTextField with the specified properties.
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
}