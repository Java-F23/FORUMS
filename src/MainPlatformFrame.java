import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPlatformFrame extends JFrame {
    private JPanel middleGrid;

    public MainPlatformFrame() {
        setTitle("Halaqa Main Platform");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize components
        initializeHeader();
        initializeGridLayout();
        initializeFooter();

        setVisible(true);
    }

    private void initializeHeader() {
        // Create header panel
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(70, 70, 70));

        // Logo or title for the platform
        ImageIcon logo = new ImageIcon("logo_1.png");
        JLabel logoLabel = new JLabel(logo, SwingConstants.CENTER);

        Image image = logo.getImage();
        int newWidth = image.getWidth(null) / 30;
        int newHeight = image.getHeight(null) / 30;
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        logoLabel = new JLabel(new ImageIcon(resizedImage), SwingConstants.CENTER);

        logoLabel.setForeground(Color.WHITE);
        header.add(logoLabel, BorderLayout.WEST);

        // Navigation Bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(70, 70, 70));

        UIManager.put("MenuItem.margin", new Insets(0, 10, 0, 10));

        // Adding menu items
        String[] menuItems = {"Home", "About", "Blog", "Contact"};
        for (String item : menuItems) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.setForeground(Color.WHITE);
            menuItem.setBackground(new Color(70, 70, 70));

            // Mouse hover effect
            menuItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    menuItem.setBackground(new Color(90, 90, 90));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    menuItem.setBackground(new Color(70, 70, 70));
                }
            });

            menuBar.add(menuItem);
        }

        header.add(menuBar, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);
    }

    private void initializeGridLayout() {
        // Left and Right Grids
        JPanel leftGrid = new JPanel();
        JPanel rightGrid = new JPanel();

        leftGrid.setBackground(new Color(240, 240, 245)); // Subtle difference from the page background
        rightGrid.setBackground(new Color(240, 240, 245));

        // Middle Grid (Posts Section)
        middleGrid = new JPanel(); // Initialize the existing 'middleGrid' instead of declaring a new one
        middleGrid.setLayout(new BoxLayout(middleGrid, BoxLayout.Y_AXIS));
        middleGrid.setBackground(new Color(245, 245, 250)); // Again, a subtle difference

        // Sample Posts
        for (int i = 1; i <= 100; i++) {
            JPanel post = new JPanel();
            post.setPreferredSize(new Dimension(900, 150)); // Fixed width and height
            post.setMaximumSize(new Dimension(900, 150)); // Fixed width and height
            post.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margins around the post
            post.setBackground(new Color(250, 250, 255));
            post.setLayout(new BorderLayout());

            // Adding some shadow for depth
            post.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 225)));

            // For rounded corners and shadow (modern look)
            post.setBorder(BorderFactory.createCompoundBorder(
                    post.getBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Internal padding for the rounded look
            ));

            JLabel postContent = new JLabel("Sample Post Content " + i, SwingConstants.CENTER);
            post.add(postContent, BorderLayout.CENTER);

            middleGrid.add(post);
        }

        // Add Grids to the main layout
        add(leftGrid, BorderLayout.WEST);
        add(rightGrid, BorderLayout.EAST);

        // Add middleGrid wrapped in a JScrollPane to enable scrolling
        JScrollPane scrollPane = new JScrollPane(middleGrid);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeFooter() {
        // Create footer panel
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(new Color(220, 220, 220)); // Light gray background for the footer

        JLabel footerLabel = new JLabel("© 2023 Halaqa Community. All rights reserved.");
        footer.add(footerLabel);

        add(footer, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new MainPlatformFrame());
    }
}
