import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class HomeContent {
    private static final Dimension POST_DIMENSION = new Dimension(900, 150);
    private static final Dimension POST_INPUT_DIMENSION = new Dimension(900, 200);
    private static final Color PRIMARY_COLOR = new Color(60, 130, 190); // Calm Blue
    private static final Color ACTION_COLOR = new Color(220, 80, 60); // Vibrant Red for actions

    private static final Color SECONDARY_COLOR = new Color(240, 240, 245); // Soft Light Gray
    private static final Color ACCENT_COLOR = new Color(45, 100, 160); // Darker Blue
    private static final Color TEXT_PRIMARY = new Color(20, 20, 20); // Almost Black
    private static final Color TEXT_SECONDARY = new Color(120, 120, 120); // Soft Gray
    private static final User testUser = new User("test_user", "test_password", UserRole.NORMAL_USER);

    private DataStorageManager dataStorageManager;
    private LeftSidebar leftSidebar;
    private JPanel rightSidebar;
    private JScrollPane scrollPane;

    private JPanel middleGrid;


    public HomeContent(DataStorageManager dataStorageManager) {
        this.dataStorageManager = dataStorageManager;
        this.scrollPane = new JScrollPane();
        this.leftSidebar = new LeftSidebar(dataStorageManager, this);
    }
    public JScrollPane  createMainContent() {
        // Create the sidebars

        JPanel leftPanel = leftSidebar.createSidebar();
        rightSidebar =RightSidebar.createSidebar("RightSidebar");

        // Create the central grid structure
        createGridStructure();

        // Container to hold the sidebars and grid structure
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(leftPanel, BorderLayout.WEST);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER); // Note: grid structure is now inside a scrollPane
        mainContentPanel.add(rightSidebar, BorderLayout.EAST);

        // Create a scroll pane for the entire content
        JScrollPane mainScrollPane = new JScrollPane(mainContentPanel);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(14);

        // Add this main scroll pane to the frame's center

        return mainScrollPane;
    }

    public JScrollPane createGridStructure() {
        middleGrid = new MainPlatformFrame.ScrollablePanel();
        middleGrid.setLayout(new BoxLayout(middleGrid, BoxLayout.Y_AXIS));
        middleGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));  // This line lets the middleGrid grow vertically.

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

        // Add contentContainer (with inputPanel and middleGrid) wrapped in a JScrollPane
        scrollPane = new JScrollPane(contentContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14); // scroll speed
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        return scrollPane;  // Return the scrollPane instead of adding it.
    }
    // Create a method to update the posts in the middle grid
    public void updateMiddleGrid(ArrayList<Post> postsToShow) {
        if (scrollPane == null || middleGrid == null) {
            System.err.println("Either scrollPane or middleGrid is not initialized!");
            return;
        }

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

        int scrollPosition = verticalScrollBar.getValue();

        middleGrid.removeAll();

        for (Post post : postsToShow) {
            JPanel postPanel = createPostPanel(post);
            middleGrid.add(postPanel);
        }

        // Update the components
        Dimension preferredSize = middleGrid.getPreferredSize();
        preferredSize.height = postsToShow.size() * POST_DIMENSION.height;
        middleGrid.setPreferredSize(preferredSize);
        middleGrid.revalidate();
        middleGrid.repaint();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                verticalScrollBar.setValue(scrollPosition);
            }
        });
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

        JButton submitButton = Navbar.createStyledButton("Post");
        submitButton.addActionListener(e -> {
            String title = postTitleField.getText();
            String content = postContentArea.getText();
            if (!title.isEmpty() && !content.isEmpty() && !title.equals("Sample Title...") && !content.equals("Sample Content.")) {
                User user = testUser;
                Post newPost = new Post(title, content, user);
                dataStorageManager.addPost(newPost);
                dataStorageManager.addUser(user);

                // Refresh the left sidebar
                leftSidebar.updateUserList();

                postTitleField.setText("");
                postContentArea.setText("");
                updateMiddleGrid(dataStorageManager.getPosts());
            }
        });

        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        inputPanel.add(postTitleField, BorderLayout.NORTH);
        inputPanel.add(postContentArea, BorderLayout.CENTER); // Added JScrollPane for larger text content
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

        JButton likesButton = Navbar.createStyledButton("Likes: " + post.getLikeCount());
        likesButton.addActionListener(e -> {
            dataStorageManager.likePost(testUser, post); // This line toggles the like/unlike state
            if(post.hasUserLiked(testUser)) {
                likesButton.setText("Unlike (" + post.getLikeCount() + ")");
            } else {
                likesButton.setText("Like (" + post.getLikeCount() + ")");
            }
        });

        JButton commentsButton = Navbar.createStyledButton("Comments: " + post.getComments().size());
        JButton reportButton = Navbar.createStyledButton("Report");

        bottomPanel.add(likesButton);
        bottomPanel.add(commentsButton);
        bottomPanel.add(reportButton);

        postPanel.add(postTitle, BorderLayout.NORTH);
        postPanel.add(postContentArea, BorderLayout.CENTER);
        postPanel.add(bottomPanel, BorderLayout.SOUTH);

        return postPanel;
    }



}
