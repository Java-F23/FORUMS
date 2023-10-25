import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    ImageIcon logo = new ImageIcon("logo_1.png");
    JLabel logoLabel = new JLabel(logo, SwingConstants.CENTER);

    public GUI() {
        // Configure the frame
        setTitle("Halaqa Community Platform");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setLayout(new GridLayout(1, 2)); // Split the frame into two halves
        setIconImage(logo.getImage());

        Image image = logo.getImage();
        int newWidth = image.getWidth(null) / 4;
        int newHeight = image.getHeight(null) / 4;
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        logoLabel = new JLabel(new ImageIcon(resizedImage), SwingConstants.CENTER);

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        // Left Panel for "Get Started"
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout()); // To center the button
        JButton getStartedButton = new JButton("Get Started");
        getStartedButton.setPreferredSize(new Dimension(150, 50));

        getStartedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the current frame
                GUI.this.setVisible(false);
                GUI.this.dispose();

                // Open the main platform frame
                new MainPlatformFrame();
            }
        });

        leftPanel.add(getStartedButton);
        leftPanel.setBackground(new Color(235, 235, 250)); // Light shade for background

        // Right Panel for Logo
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout()); // To center the logo
        rightPanel.add(logoLabel, BorderLayout.CENTER);

        // Add panels to the frame
        add(leftPanel);
        add(rightPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }
}
