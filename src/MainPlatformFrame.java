import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainPlatformFrame extends JFrame {
    private static final Dimension POST_DIMENSION = new Dimension(900, 150);
    private static final Dimension POST_INPUT_DIMENSION = new Dimension(900, 200);
    private static final Color PRIMARY_COLOR = new Color(60, 130, 190); // Calm Blue
    private static final Color SECONDARY_COLOR = new Color(240, 240, 245); // Soft Light Gray
    private static final Color ACCENT_COLOR = new Color(45, 100, 160); // Darker Blue
    private static final Color ACTION_COLOR = new Color(220, 80, 60); // Vibrant Red for actions
    private static final Color TEXT_PRIMARY = new Color(20, 20, 20); // Almost Black
    private static final Color TEXT_SECONDARY = new Color(120, 120, 120); // Soft Gray
    private static final User testUser = new User("test_user", "test_password", UserRole.NORMAL_USER);


    private JPanel middleGrid;
    private DataStorageManager dataStorageManager;

    public MainPlatformFrame() {
        dataStorageManager = ProjectPopulator.populate();
        initializeFrame();
    }

    private void initializeFrame() {
        setTitle("Halaqa Main Platform");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        createGridStructure();
        add(createFooter(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(1600, 45));
        header.setBackground(new Color(60, 130, 190));

        // Create right panel for the logo image aligned to the left of the panel
        ImageIcon logo = new ImageIcon("logo.png");
        Image image = logo.getImage();
        int newWidth = image.getWidth(null) / 15;
        int newHeight = image.getHeight(null) / 15;
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(resizedImage));

        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        header.add(logoLabel, BorderLayout.WEST);

        // Create left panel for the logout button aligned to the right of the panel
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        JButton logoutButton = createStyledButton("Logout");
        logoutPanel.add(logoutButton);
        logoutPanel.setBackground(new Color(60, 130, 190));
        header.add(logoutPanel, BorderLayout.EAST);

        // Create middle panel for the 3 buttons aligned to the center of the panel and spread equally with spacing
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 200, 6));
        navigationPanel.setBackground(new Color(60, 130, 190));
        JButton homeButton = createStyledButton("Home");
        JButton profileButton = createStyledButton("Profile");
        JButton settingsButton = createStyledButton("Settings");

        navigationPanel.add(homeButton);
        navigationPanel.add(profileButton);
        navigationPanel.add(settingsButton);
        header.add(navigationPanel, BorderLayout.CENTER);

        return header;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(button.getBackground().brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(60, 130, 190));
            }
        });

        if (text.equals("Logout")){
            button.setFocusPainted(false);
            button.setBackground(new Color(180, 80, 60));
            button.setFont(new Font("Arial", Font.BOLD, 18));
            return button;
        }
        button.setFont(new Font("Calibri", Font.PLAIN, 20));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(new Color(60, 130, 190));
        button.setForeground(Color.WHITE); // Setting text color to white for contrast against PRIMARY_COLOR

        return button;
    }


    private void createGridStructure() {
        // Create left, middle, and right grids
        JPanel leftGrid = new JPanel();
        JPanel rightGrid = new JPanel();
        rightGrid.setBackground(ACTION_COLOR);
        // Create the middle grid (Posts Section)
        middleGrid = new JPanel();
        middleGrid.setLayout(new BoxLayout(middleGrid, BoxLayout.Y_AXIS));

        // Retrieve posts from DataStorageManager
        ArrayList<Post> posts = dataStorageManager.getPosts();
        for (Post post : posts) {
            JPanel postPanel = createPostPanel(post);
            middleGrid.add(postPanel);
        }

        // Create a panel for the input panel
        JPanel inputPanelWrapper = new JPanel(new BorderLayout());
        inputPanelWrapper.setLayout(new BoxLayout(inputPanelWrapper, BoxLayout.Y_AXIS));
        inputPanelWrapper.add(createInputPanel(), BorderLayout.CENTER);
        // Add vertical spacing between inputPanelWrapper and middleGrid
        inputPanelWrapper.add(Box.createVerticalStrut(20)); // Adjust the spacing as needed


        // Create a container for the input panel and the middle grid
        JPanel contentContainer = new JPanel();
        contentContainer.setLayout(new BorderLayout());
        contentContainer.add(inputPanelWrapper, BorderLayout.NORTH);
        contentContainer.add(middleGrid, BorderLayout.CENTER);

        // Add Grids to the main layout
        add(leftGrid, BorderLayout.WEST);
        add(rightGrid, BorderLayout.EAST);

        // Add contentContainer (with inputPanel and middleGrid) wrapped in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(contentContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14); // scroll speed
        add(scrollPane, BorderLayout.CENTER);
    }

    // Create a method to update the posts in the middle grid
    private void updateMiddleGrid() {
        middleGrid.removeAll();
        ArrayList<Post> posts = dataStorageManager.getPosts();
        for (Post post : posts) {
            JPanel postPanel = createPostPanel(post);
            middleGrid.add(postPanel);
        }
        middleGrid.revalidate();
        middleGrid.repaint();
    }
    private JPanel createInputPanel() {
        // Set the attributes for inputPanel
        JPanel inputPanel = new JPanel();
        inputPanel.setPreferredSize(POST_INPUT_DIMENSION); // Fixed width and height
        inputPanel.setMaximumSize(POST_INPUT_DIMENSION); // Fixed width and height
        inputPanel.setBackground(SECONDARY_COLOR);
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));


        JTextField postTitleField = new JTextField("Sample Title...");
        JTextArea postContentArea = new JTextArea("Sample Content.");

        // Add focus listeners for placeholders
        postTitleField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (postTitleField.getText().equals("Sample Title...")) {
                    postTitleField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (postTitleField.getText().isEmpty()) {
                    postTitleField.setText("Sample Title...");
                }
            }
        });

        postContentArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (postContentArea.getText().equals("Sample Content.")) {
                    postContentArea.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (postContentArea.getText().isEmpty()) {
                    postContentArea.setText("Sample Content.");
                }
            }
        });

        postTitleField.setFont(new Font("Arial", Font.BOLD, 20));
        postTitleField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        postContentArea.setWrapStyleWord(true);
        postContentArea.setLineWrap(true);
        postContentArea.setFont(new Font("Calibri", Font.PLAIN, 18));
        postContentArea.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton submitButton = createStyledButton("Post");
        submitButton.addActionListener(e -> {
            String title = postTitleField.getText();
            String content = postContentArea.getText();
            if (!title.isEmpty() && !content.isEmpty() && !title.equals("Sample Title...") && !content.equals("Sample Content.")) {
                User user = testUser;
                Post newPost = new Post(title, content, user);
                dataStorageManager.addPost(newPost);
                postTitleField.setText("");
                postContentArea.setText("");
                updateMiddleGrid();
            }
        });
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        inputPanel.add(postTitleField, BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(postContentArea), BorderLayout.CENTER); // Added JScrollPane for larger text content
        inputPanel.add(submitButton, BorderLayout.SOUTH);

        return inputPanel;
    }


    private JPanel createPostPanel(Post post) {
        JPanel postPanel = new JPanel();
        postPanel.setPreferredSize(POST_DIMENSION); // Fixed width and height
        postPanel.setMaximumSize(POST_DIMENSION);
        postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        postPanel.setBackground(Color.WHITE);
        postPanel.setLayout(new BorderLayout());

        // Add a border at the bottom with some padding for visual separation
        postPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, ACCENT_COLOR.darker()),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));

        JLabel postTitle = new JLabel(post.getTitle());
        postTitle.setFont(new Font("Arial", Font.BOLD, 20));
        postTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JTextArea postContentArea = new JTextArea(post.getContent());
        postContentArea.setWrapStyleWord(true);
        postContentArea.setLineWrap(true);
        postContentArea.setOpaque(false);
        postContentArea.setEditable(false);
        postContentArea.setFont(new Font("Calibri", Font.PLAIN, 18));
        postContentArea.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Create a panel for the bottom elements
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        JButton likesButton = createStyledButton("Likes: " + post.getLikeCount());
        JButton commentsButton = createStyledButton("Comments: " + post.getComments().size());
        JButton reportButton = createStyledButton("Report");

        bottomPanel.add(likesButton);
        bottomPanel.add(commentsButton);
        bottomPanel.add(reportButton);

        postPanel.add(postTitle, BorderLayout.NORTH);
        postPanel.add(postContentArea, BorderLayout.CENTER);
        postPanel.add(bottomPanel, BorderLayout.SOUTH);

        return postPanel;
    }
    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(SECONDARY_COLOR);
        JLabel footerLabel = new JLabel("© 2023 Halaqa Community. All rights reserved.");
        footer.add(footerLabel);
        return footer;
    }
}
