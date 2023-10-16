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

public class PopularPairsReportPanel{
    public JPanel popularPairsReportPanel;

    public PopularPairsReportPanel(){
        this.popularPairsReportPanel = new JPanel();
        popularPairsReportPanel.setLayout(new GridBagLayout());

        JPanel dateEntryRow = new JPanel();
        JLabel startDateLabel = new JLabel("Start Date:");
        JTextField startDateMonthField = getTextFieldWithText("MM",2);
        JTextField startDateDayField = getTextFieldWithText("DD",2);
        JTextField startDateYearField = getTextFieldWithText("YYYY",4);
        JLabel endDateLabel = new JLabel("End Date:");
        JTextField endDateMonthField = getTextFieldWithText("MM",2);
        JTextField endDateDayField = getTextFieldWithText("DD",2);
        JTextField endDateYearField = getTextFieldWithText("YYYY",4);
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // validate user input is correct
                String startDateMonth = startDateMonthField.getText();
                String startDateDay = startDateDayField.getText();
                String startDateYear = startDateYearField.getText();
                String endDateMonth = endDateMonthField.getText();
                String endDateDay = startDateDayField.getText();
                String endDateYear = startDateYearField.getText();
                // valiate user input
                // ?if (startDateMonthField.getText() == "MM")
                updateResulingPopularPairs();
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

        JPanel resultRow = new JPanel();
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

        popularPairsReportPanel.add(dateEntryRow, dateEntryRowConstraints);
        popularPairsReportPanel.add(resultRow, resultRowConstraints);
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

    public void updateResulingPopularPairs(){
        
    }

    // public JPanel resulingPopularPairs(List )

    public JPanel getPopularPairsReportPanel(){
        return this.popularPairsReportPanel;
    }


}