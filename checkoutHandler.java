import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class checkoutHandler {
    private static JFrame checkoutFrame;

    public static void checkoutFrame_() {
        SwingUtilities.invokeLater(() -> {
            checkoutFrame = new JFrame();
            checkoutFrame.setTitle("Drink Customization Panel");
            checkoutFrame.setSize(GUI.screenSize.width, GUI.screenSize.height);
            checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            checkoutFrame.setLayout(new BorderLayout());

            // Create a panel with drinks for the selected category
            checkoutFrame.add(GUI.checkoutPanel, BorderLayout.CENTER);

            checkoutFrame.setVisible(true);
        });
    }
}