import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeContent {
    private static final Dimension POST_DIMENSION = new Dimension(900, 150);
    private static final Dimension POST_INPUT_DIMENSION = new Dimension(900, 200);
    private static final Color PRIMARY_COLOR = new Color(60, 130, 190); // Calm Blue
    private static final Color ACTION_COLOR = new Color(220, 80, 60); // Vibrant Red for actions

    private static final Color SECONDARY_COLOR = new Color(240, 240, 245); // Soft Light Gray
    private static final Color ACCENT_COLOR = new Color(45, 100, 160); // Darker Blue
    private static final Color TEXT_PRIMARY = new Color(20, 20, 20); // Almost Black
    private static final Color TEXT_SECONDARY = new Color(120, 120, 120); // Soft Gray

    private Map<Integer, JButton> likeButtonsMap;

    private InputPanel inputPanel;
    private LeftSidebar leftSidebar;
    private RightSidebar rightSidebar;
    private JScrollPane scrollPane;
    private JPanel middleGrid;
    private MainController mainController; // Use this to interact with the model
    JButton likesButton;

    public HomeContent(MainController controller) {
        this.mainController = controller;
        this.scrollPane = new JScrollPane();
        this.leftSidebar = new LeftSidebar(controller);
        this.rightSidebar = new RightSidebar();
        this.inputPanel = new InputPanel(e -> handlePostSubmission());
        likeButtonsMap = new HashMap<>();
    }

    public void handlePostSubmission() {
        String title = inputPanel.getPostTitle();
        String content = inputPanel.getPostContent();
        mainController.handlePostSubmission(title, content);
        inputPanel.clearInputFields();
    }

    public JScrollPane createMainContent() {
        JPanel leftPanel = leftSidebar.createSidebar();
        leftPanel.setBackground(Color.WHITE);
        JPanel rightPanel = rightSidebar.createSidebar();

        createGridStructure();
        updateStatistics();

        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(leftPanel, BorderLayout.WEST);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);
        mainContentPanel.add(rightPanel, BorderLayout.EAST);

        JScrollPane mainScrollPane = new JScrollPane(mainContentPanel);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(14);

        return mainScrollPane;
    }
    public void updateStatistics() {
        // Assuming rightSidebar is a class member and is already initialized
        mainController.updateStatisticsInView(rightSidebar);
    }


    public JScrollPane createGridStructure() {
        middleGrid = new MainPlatformFrame.ScrollablePanel();
        middleGrid.setLayout(new BoxLayout(middleGrid, BoxLayout.Y_AXIS));
        middleGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        ArrayList<Post> posts = mainController.getPosts();
        for (Post post : posts) {
            JPanel postPanel = createPostPanel(post);
            middleGrid.add(postPanel);
        }

        JPanel inputPanelWrapper = new JPanel(new BorderLayout());
        inputPanelWrapper.setLayout(new BoxLayout(inputPanelWrapper, BoxLayout.Y_AXIS));
        inputPanelWrapper.add(inputPanel.getPanel(), BorderLayout.CENTER);
        inputPanelWrapper.add(Box.createVerticalStrut(20));

        JPanel contentContainer = new JPanel();
        contentContainer.setLayout(new BorderLayout());
        contentContainer.add(inputPanelWrapper, BorderLayout.NORTH);
        contentContainer.add(middleGrid, BorderLayout.CENTER);

        scrollPane = new JScrollPane(contentContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        return scrollPane;
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
        likesButton.addActionListener(e -> mainController.toggleLikePost(post, likesButton));
        likeButtonsMap.put(post.getPostID(), likesButton); // Store the button with the post's ID as key

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

    public void refreshLikes(Post post) {
        JButton likeButton = likeButtonsMap.get(post.getPostID()); // Get the button for this post
        if (likeButton != null) {
            if (post.hasUserLiked(mainController.getCurrentUser())) {
                likeButton.setText("Unlike (" + post.getLikeCount() + ")");
            } else {
                likeButton.setText("Like (" + post.getLikeCount() + ")");
            }
        }
    }


    public LeftSidebar getLeftSidebar() {
        return leftSidebar;
    }

    public JButton getLikesButton() {
        return likesButton;
    }
}

