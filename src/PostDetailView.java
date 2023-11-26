import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PostDetailView extends JDialog {
    private static final Dimension POST_DETAIL_DIMENSION = new Dimension(600, 400);

    private Post post;
    private JTextArea postContentArea;
    private JButton addCommentButton;
    private JPanel commentsPanel;

    private MainController mainController;

    public PostDetailView(Post post, MainController mainController) {
        this.mainController = mainController;
        this.post = post;
        setTitle("Post Details");
        setSize(POST_DETAIL_DIMENSION);
        setLocationRelativeTo(null); // Center the dialog on the screen
        setModal(true);

        createComponents();
        layoutComponents();
        updateCommentsPanel();
    }

    private void createComponents() {
        postContentArea = new JTextArea(post.getContent());
        postContentArea.setEditable(false);
        addCommentButton = new JButton("Add Comment");
        commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));

        addCommentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddComment();
            }
        });
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Post content area
        JScrollPane postContentScrollPane = new JScrollPane(postContentArea);
        postContentScrollPane.setPreferredSize(new Dimension(POST_DETAIL_DIMENSION.width, 80));

        // Add Comment button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addCommentButton);

        // Comments panel
        JPanel commentsPanel = createCommentsPanel(); // Create a method to generate comments panel
        JScrollPane commentsScrollPane = new JScrollPane(commentsPanel);
        commentsScrollPane.setPreferredSize(new Dimension(POST_DETAIL_DIMENSION.width, POST_DETAIL_DIMENSION.height - 100));

        // Add components to the main layout
        add(postContentScrollPane, BorderLayout.NORTH);
        add(commentsScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createCommentsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Add some spacing at the top

        for (Comment comment : post.getComments()) {
            JPanel commentPanel = createCommentPanel(comment);
            panel.add(commentPanel);
            // Add a separator between comments (a line or a space)
            panel.add(Box.createRigidArea(new Dimension(0, 10))); // Adjust the height as needed
        }

        return panel;
    }

    private JPanel createCommentPanel(Comment comment) {
        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BorderLayout());
        commentPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY)); // Add a top border

        JLabel userLabel = new JLabel(comment.getAuthor().getUsername());
        JLabel contentLabel = new JLabel(comment.getContent());

        // Add upvote/downvote symbols as needed

        // Add components to the comment panel
        commentPanel.add(userLabel, BorderLayout.WEST);
        commentPanel.add(contentLabel, BorderLayout.CENTER);

        return commentPanel;
    }

    private void handleAddComment() {
        String commentContent = JOptionPane.showInputDialog("Enter your comment:");
        if (commentContent != null && !commentContent.isEmpty()) {
            // Assuming you have a method to add comments to the post in your MainController
            mainController.addComment(new Comment(post, mainController.getCurrentUser(), commentContent));
            // Refresh the UI or take appropriate action
            updateCommentsPanel();
        }
    }

    private void updateCommentsPanel() {
        commentsPanel.removeAll();

        // Get the comments related to the current post
        ArrayList<Comment> comments = mainController.getCommentsForPost(post);

        for (Comment comment : comments) {
            // Create a panel to display each comment
            JPanel commentPanel = createCommentPanel(comment);
            commentsPanel.add(commentPanel);
        }

        commentsPanel.revalidate();
        commentsPanel.repaint();
    }

}
