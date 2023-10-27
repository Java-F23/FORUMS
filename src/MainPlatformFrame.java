import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainPlatformFrame extends JFrame {
    private JPanel middleGrid;
    private DataStorageManager dataStorageManager;

    public MainPlatformFrame() {
    }

    public MainPlatformFrame(DataStorageManager ds) {
        dataStorageManager = ds;
        initializeFrame();
    }

    private void initializeFrame() {
        setTitle("Halaqa Main Platform");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createGridStructure();
        initializeHeader();
        initializeFooter();

        setVisible(true);
    }
    private void initializeHeader() {
        // Create the main header panel with a BorderLayout
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(70, 70, 70));

        // Create the left panel for the logo
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(70, 70, 70));

        // Create the logo
        ImageIcon logo = new ImageIcon("logo_1.png");
        JLabel logoLabel = new JLabel(logo, SwingConstants.CENTER);
        Image image = logo.getImage();
        int newWidth = image.getWidth(null) / 30;
        int newHeight = image.getHeight(null) / 30;
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        logoLabel = new JLabel(new ImageIcon(resizedImage), SwingConstants.CENTER);
        logoLabel.setForeground(Color.WHITE);
        leftPanel.add(logoLabel);

        // Create the middle panel for menu items
        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(new Color(70, 70, 70));

        // Create navigation bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(70, 70, 70));

        // Increase the width and height of menu items
        Dimension menuItemSize = new Dimension(200, 100);

        // Adjust margin and padding
        UIManager.put("MenuItem.margin", new Insets(10, 10, 10, 10));

        String[] menuItems = {"Home", "Profile", "Settings", "Logout"};
        for (String item : menuItems) {
            JMenu menu = new JMenu(item);
            menu.setForeground(Color.WHITE);
            menu.setBackground(new Color(70, 70, 70));
            menu.setHorizontalAlignment(SwingConstants.CENTER); // Center-align text
            menu.setPreferredSize(menuItemSize);

            // Mouse hover effect
            menu.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    menu.setBackground(new Color(200, 90, 90));
                    menu.setForeground(Color.CYAN); // Change text color on hover
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    menu.setBackground(new Color(70, 70, 70));
                    menu.setForeground(Color.WHITE); // Reset text color on exit
                }
            });

            menuBar.add(menu);
        }

        middlePanel.add(menuBar);

        // Create the right panel (empty)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(70, 70, 70));

        // Add the left panel to the west (left) of the header
        header.add(leftPanel, BorderLayout.WEST);

        // Add the middle panel to the center of the header
        header.add(middlePanel, BorderLayout.CENTER);

        // Add the right panel to the east (right) of the header
        header.add(rightPanel, BorderLayout.EAST);

        // Add the main header panel to the frame
        add(header, BorderLayout.NORTH);
    }

    private void createGridStructure() {
        // Create left, middle, and right grids
        JPanel leftGrid = new JPanel();
        JPanel rightGrid = new JPanel();
        leftGrid.setBackground(new Color(240, 240, 245));
        rightGrid.setBackground(new Color(240, 240, 245));

        // Create the middle grid (Posts Section)
        middleGrid = new JPanel();
        middleGrid.setLayout(new BoxLayout(middleGrid, BoxLayout.Y_AXIS));
        middleGrid.setBackground(new Color(245, 245, 250));

        // Retrieve posts from DataStorageManager
        ArrayList<Post> posts = dataStorageManager.getPosts();
        for (Post post : posts) {
            JPanel postPanel = createPostPanel(post);
            middleGrid.add(postPanel);
        }

        // Add Grids to the main layout
        add(leftGrid, BorderLayout.WEST);
        add(rightGrid, BorderLayout.EAST);

        // Add middleGrid wrapped in a JScrollPane to enable scrolling
        JScrollPane scrollPane = new JScrollPane(middleGrid);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14); // scroll speed

        add(scrollPane, BorderLayout.CENTER);
    }


    private JPanel createPostPanel(Post post) {
        JPanel postPanel = new JPanel();
        postPanel.setPreferredSize(new Dimension(900, 150)); // Fixed width and height
        postPanel.setMaximumSize(new Dimension(900, 150)); // Fixed width and height
        postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        postPanel.setBackground(new Color(250, 250, 255));
        postPanel.setLayout(new BorderLayout());
        postPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 225)));
        postPanel.setBorder(BorderFactory.createCompoundBorder(
                postPanel.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JLabel postTitle = new JLabel("<html><b>" + post.getTitle() + "</b></html>", SwingConstants.CENTER);
        JLabel postContent = new JLabel(post.getContent(), SwingConstants.CENTER);

        postPanel.add(postTitle, BorderLayout.NORTH);
        postPanel.add(postContent, BorderLayout.CENTER);

        return postPanel;
    }

    private void initializeFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(new Color(220, 220, 220));
        JLabel footerLabel = new JLabel("© 2023 Halaqa Community. All rights reserved.");
        footer.add(footerLabel);
        add(footer, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        ProjectPopulator populate = new ProjectPopulator();
        populate.main(args);
        DataStorageManager ds = populate.getDataStorageManager();
        SwingUtilities.invokeLater(() -> new MainPlatformFrame(ds));
    }
}
