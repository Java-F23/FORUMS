import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class InputPanel {
    private static final Dimension POST_DIMENSION = new Dimension(900, 150);
    private static final Dimension POST_INPUT_DIMENSION = new Dimension(900, 200);
    private static final Color SECONDARY_COLOR = new Color(240, 240, 245); // Soft Light Gray

    private final JPanel panel;
    private final JTextField postTitleField;
    private final JTextArea postContentArea;

    public InputPanel(ActionListener submitListener) {
        panel = new JPanel();
        panel.setPreferredSize(POST_INPUT_DIMENSION);
        panel.setMaximumSize(POST_INPUT_DIMENSION);
        panel.setBackground(SECONDARY_COLOR);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        postTitleField = new JTextField("Sample Title...");
        postContentArea = new JTextArea("Sample Content.");

        // Add focus listeners for placeholders
        addFocusListeners(postTitleField, "Sample Title...");
        addFocusListeners(postContentArea, "Sample Content.");

        postTitleField.setFont(new Font("Arial", Font.BOLD, 20));
        postTitleField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        postContentArea.setWrapStyleWord(true);
        postContentArea.setLineWrap(true);
        postContentArea.setFont(new Font("Calibri", Font.PLAIN, 18));
        postContentArea.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton submitButton = Navbar.createStyledButton("Post");
        submitButton.addActionListener(submitListener);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panel.add(postTitleField, BorderLayout.NORTH);
        panel.add(postContentArea, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getPostTitle() {
        return postTitleField.getText();
    }

    public String getPostContent() {
        return postContentArea.getText();
    }

    public void clearInputFields() {
        postTitleField.setText("");
        postContentArea.setText("");
    }

    private void addFocusListeners(JTextField textField, String placeholder) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                }
            }
        });
    }

    private void addFocusListeners(JTextArea textArea, String placeholder) {
        textArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(placeholder)) {
                    textArea.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().isEmpty()) {
                    textArea.setText(placeholder);
                }
            }
        });
    }
}
