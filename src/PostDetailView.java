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

    // Inside the handleAddComment method
    private void handleAddComment() {
        String commentContent = JOptionPane.showInputDialog("Enter your comment:");
        if (commentContent != null && !commentContent.isEmpty()) {
            // Create a new Comment
            Comment newComment = new Comment(post, mainController.getCurrentUser(), commentContent);

            // Initialize upvotes and downvotes
            newComment.setUpvotes(0);
            newComment.setDownvotes(0);

            // Call the MainController to add the comment
            mainController.addComment(newComment, post);
            updateCommentsPanel();

            // Assume there is a method to get the updated comments count
            int commentsCount = mainController.getCommentsForPost(post).size();

            // Update the commentsButton text and count in the PostPanel
            commentsButton.setText("Comments: " + commentsCount);
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
        JLabel upVotesLabel = new JLabel("Upvotes: " + comment.getUpvotes());
        JLabel downVotesLabel = new JLabel("Downvotes: " + comment.getDownvotes());

        JButton upvoteButton = new JButton("U");
        JButton downvoteButton = new JButton("D");

        upvoteButton.addActionListener(e -> handleUpvote(comment));
        downvoteButton.addActionListener(e -> handleDownvote(comment));

        JPanel votesPanel = new JPanel();
        votesPanel.setLayout(new BoxLayout(votesPanel, BoxLayout.Y_AXIS));
        votesPanel.add(upVotesLabel);
        votesPanel.add(downVotesLabel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(upvoteButton);
        buttonsPanel.add(downvoteButton);

        commentPanel.add(commentTextArea, BorderLayout.CENTER);
        commentPanel.add(userInfoLabel, BorderLayout.NORTH);
        commentPanel.add(votesPanel, BorderLayout.SOUTH);
        commentPanel.add(buttonsPanel, BorderLayout.EAST);

        return commentPanel;
    }


    // New methods for handling upvote and downvote
    private void handleUpvote(Comment comment) {
        mainController.upvoteComment(comment);
        updateCommentsPanel();
    }

    private void handleDownvote(Comment comment) {
        mainController.downvoteComment(comment);
        updateCommentsPanel();
    }

}
