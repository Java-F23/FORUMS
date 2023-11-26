import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

// Separate class for the post panel
public class PostPanel extends JPanel {
    private static final Dimension POST_DIMENSION = new Dimension(900, 150);
    private static final Color PRIMARY_COLOR = new Color(60, 130, 190); // Calm Blue
    private static final Color ACTION_COLOR = new Color(220, 80, 60); // Vibrant Red for actions

    private static final Color SECONDARY_COLOR = new Color(240, 240, 245); // Soft Light Gray
    private static final Color ACCENT_COLOR = new Color(45, 100, 160); // Darker Blue

    private JLabel postTitle;
    private JLabel userInfo;
    private JLabel postDate;
    private JTextArea postContentArea;
    private JButton likesButton;
    private JLabel viewsLabel;
    private JButton commentsButton;
    private JButton reportButton;
    private Post post;
    private MainController mainController;

    public PostPanel(Post post, MainController mainController) {
        this.post = post;
        this.mainController = mainController;
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(POST_DIMENSION);
        setMaximumSize(POST_DIMENSION);
        setBackground(Color.WHITE);
        createComponents();
        layoutComponents();
    }

    private void createComponents() {
        // Create UI components here...
        postTitle = new JLabel(post.getTitle());
        userInfo = new JLabel( post.getAuthor().getUsername());
        postDate = new JLabel(new SimpleDateFormat("HH:mm d MMM").format(post.getTimestamp()));
        postContentArea = new JTextArea(post.getContent());
        likesButton = Navbar.createStyledButton("Likes: " + post.getLikeCount());
        viewsLabel = new JLabel("Views: " + post.getViewCount());
        commentsButton = Navbar.createStyledButton("Comments: " + post.getComments().size());
        reportButton = Navbar.createStyledButton("Report");

        reportButton.addActionListener(e -> handleReport());

        commentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePostDetails();
            }
        });


        // Add action listeners here...
        likesButton = Navbar.createStyledButton("Likes: " + post.getLikeCount());
        likesButton.addActionListener(e -> mainController.toggleLikePost(post));

        // Register the like button with the controller
        mainController.registerLikeButtonForPost(post, likesButton);
    }
    private void layoutComponents() {
        // Layout components here...
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, ACCENT_COLOR.brighter()),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel postTitle = new JLabel(post.getTitle());
        postTitle.setFont(new Font("Arial", Font.BOLD, 20));
        userInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        postDate.setFont(new Font("Arial", Font.PLAIN, 12));

        // Add user info and date to a sub-panel on the right
        JPanel userInfoPanel = new JPanel(new BorderLayout());
        userInfoPanel.add(userInfo, BorderLayout.NORTH);
        userInfoPanel.add(postDate, BorderLayout.SOUTH);
        userInfoPanel.setOpaque(false);

        headerPanel.add(postTitle, BorderLayout.WEST);
        headerPanel.add(userInfoPanel, BorderLayout.EAST);

        // CONTENT AREA
        postContentArea.setWrapStyleWord(true);
        postContentArea.setLineWrap(true);
        postContentArea.setOpaque(false);
        postContentArea.setEditable(false);
        postContentArea.setFont(new Font("Calibri", Font.PLAIN, 18));
        postContentArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.setOpaque(false);
        viewsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bottomPanel.add(likesButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add space between buttons
        bottomPanel.add(commentsButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add space between buttons
        bottomPanel.add(viewsLabel);
        bottomPanel.add(Box.createHorizontalGlue()); // Pushes everything to the left
        bottomPanel.add(reportButton);

        // Adding components to the postPanel
        add(headerPanel, BorderLayout.NORTH);
        add(postContentArea, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void handleReport() {
        String reason = JOptionPane.showInputDialog("Please provide a reason for reporting:");
        if (reason != null && !reason.isEmpty()) {
            mainController.handlePostReport(post, reason);
            reportButton.setBackground(ACTION_COLOR);
            reportButton.setEnabled(false);
        }
    }

    // Additional methods to update likes, comments etc. can be added here.
    public void refreshLikes() {
        likesButton.setText("Likes: " + post.getLikeCount());
    }

    private void handlePostDetails() {
        // Open the PostDetailView with the selected post
        PostDetailView postDetailView = new PostDetailView(post, mainController);
        postDetailView.setVisible(true);
    }

};
