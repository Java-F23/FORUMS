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
    private JButton commentsButton;

    public PostDetailView(Post post, MainController mainController, JButton commentsButton) {
        this.mainController = mainController;
        this.post = post;
        this.commentsButton = commentsButton;
        setTitle(post.getTitle());
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
        postContentScrollPane.setPreferredSize(new Dimension(POST_DETAIL_DIMENSION.width, 50));

        // Add Comment button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addCommentButton);

        // Comments panel
        JScrollPane commentsScrollPane = new JScrollPane(commentsPanel);
        commentsScrollPane.setPreferredSize(new Dimension(POST_DETAIL_DIMENSION.width, POST_DETAIL_DIMENSION.height - 200));

        // Add components to the main layout
        add(postContentScrollPane, BorderLayout.NORTH);
        add(commentsScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void handleAddComment() {
        String commentContent = JOptionPane.showInputDialog("Enter your comment:");
        if (commentContent != null && !commentContent.isEmpty()) {
            // Assuming you have a method to add comments to the post in your MainController
            post.addComment(new Comment(post, mainController.getCurrentUser(), commentContent));
            // Refresh the UI or take appropriate action
            updateCommentsPanel();

            // Update the commentsButton text and count in the PostPanel
            commentsButton.setText("Comments: " + post.getComments().size());
        }
    }

    private void updateCommentsPanel() {
        commentsPanel.removeAll();

        // Get the comments directly from the post
        ArrayList<Comment> comments = post.getComments();

        for (Comment comment : comments) {
            // Create a panel to display each comment
            JPanel commentPanel = createCommentPanel(comment);
            commentsPanel.add(commentPanel);
        }

        commentsPanel.revalidate();
        commentsPanel.repaint();
    }

    private JPanel createCommentPanel(Comment comment) {
        JPanel commentPanel = new JPanel(new BorderLayout());
        JTextArea commentTextArea = new JTextArea(comment.getContent());
        commentTextArea.setEditable(false);
        JLabel userInfoLabel = new JLabel("User: " + comment.getAuthor().getUsername());
        JLabel likesLabel = new JLabel("Likes: " + comment.getUpvotes());

        commentPanel.add(commentTextArea, BorderLayout.CENTER);
        commentPanel.add(userInfoLabel, BorderLayout.NORTH);
        commentPanel.add(likesLabel, BorderLayout.SOUTH);

        return commentPanel;
    }
}
