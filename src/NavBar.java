import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavBar extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Facebook-like Navbar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLayout(new BorderLayout());

        Navbar navbar = new Navbar();

        frame.add(navbar, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}

class Navbar extends JPanel {
    public Navbar() {
        setLayout(new BorderLayout());

        // Left section
        JPanel leftSection = new JPanel();
        leftSection.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftSection.setBackground(new Color(24, 119, 242));

        // Create the logo
        ImageIcon logo = new ImageIcon("logo_1.png");
        JLabel logoLabel = new JLabel(logo, SwingConstants.CENTER);
        Image image = logo.getImage();
        int newWidth = image.getWidth(null) / 50;
        int newHeight = image.getHeight(null) / 50;
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        logoLabel = new JLabel(new ImageIcon(resizedImage), SwingConstants.CENTER);
        logoLabel.setForeground(Color.WHITE);
        leftSection.add(logoLabel);

        // Middle section
        JPanel middleSection = new JPanel();
        middleSection.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        middleSection.setBackground(new Color(24, 119, 242));

        JButton homeButton = createMenuItem("Home");
        JButton profileButton = createMenuItem("Profile");
        JButton settingsButton = createMenuItem("Settings");

        middleSection.add(homeButton);
        middleSection.add(profileButton);
        middleSection.add(settingsButton);

        // Right section
        JPanel rightSection = new JPanel();
        rightSection.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        rightSection.setBackground(new Color(24, 119, 242));

        JLabel logoutLabel = new JLabel("Logout");
        logoutLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        logoutLabel.setForeground(Color.WHITE);
        rightSection.add(logoutLabel);

        add(leftSection, BorderLayout.WEST);
        add(middleSection, BorderLayout.CENTER);
        add(rightSection, BorderLayout.EAST);
    }

    private JButton createMenuItem(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBackground(new Color(24, 119, 242));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(15, 100, 15, 100));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your action here
                JOptionPane.showMessageDialog(null, "You clicked " + text);
            }
        });
        return button;
    }
}
