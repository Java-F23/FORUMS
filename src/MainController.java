import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    private MainPlatformFrame mainFrame;
    private DataStorageManager model;
    private User currentUser = new User("testUser", "testPassword", UserRole.ADMIN);
    private Map<Integer, JButton> postLikeButtons = new HashMap<>();

    public MainController() {
        // Initialize the data model
        model = ProjectPopulator.populate();
        // Initialize the frame with the controller
        mainFrame = new MainPlatformFrame(this);
        // Refresh the UI with the initial data
        updateMiddleGrid();
    }


    public ArrayList<Post> getPosts(){
        return  model.getPosts();
    }
    public ArrayList<User> getUsers() {
        return model.getUsers();
    }

    public void addPost(Post newPost) {
        model.addPost(newPost);
    }

    public void addUser(User user) {
        model.addUser(user);
    }


    public void updateMiddleGrid() {
        // Call the method in HomeContent to update the middle grid
        // Call the method in HomeContent to update the middle grid
        mainFrame.getHomeContent().updateMiddleGrid(getPosts());

        // Update the left sidebar with the latest user data
        refreshUserListInView();

        // Update the right sidebar with the latest statistics
        updateStatisticsInView(mainFrame.getHomeContent().getRightSidebar());
    }

    public void refreshUserListInView() {
        mainFrame.getHomeContent().getLeftSidebar().updateUserList(getUsers());
    }
    public void searchPostsAndUpdateView(String keyword) {
        ArrayList<Post> matchingPosts = model.searchPosts(keyword);
        mainFrame.getHomeContent().updateMiddleGrid(matchingPosts);
    }

    public void handlePostSubmission(String title, String content) {
        // Create a new Post with the provided title and content
        Post newPost = new Post(title, content, currentUser);

        // Add the new post to the data storage manager
        model.addUser(currentUser);
        model.addPost(newPost);

        // Update the middle grid
        updateMiddleGrid();
        refreshUserListInView();
    }

    public Map<String, Object> generateStatistics() {
        return  model.generateStatistics();
    }

    public void registerLikeButtonForPost(Post post, JButton likeButton) {
        postLikeButtons.put(post.getPostID(), likeButton);
        refreshLikeButton(post);
    }

    public void toggleLikePost(Post post) {
        boolean isLiked = model.likePost(currentUser, post); // Toggle like status in the model
        refreshLikeButton(post); // Refresh the UI for the like button associated with this post

        // Update the statistics for both posts and comments in the right panel
        updateStatisticsInView(mainFrame.getHomeContent().getRightSidebar());
    }

    private void refreshLikeButton(Post post) {
        JButton likeButton = postLikeButtons.get(post.getPostID());
        if (likeButton != null) {
            updateLikeButton(likeButton, post, model.hasUserLikedPost(currentUser, post));
        }
    }

    public void updateLikeButton(JButton likeButton, Post post, boolean isLiked) {
        // Update the text of the like button to reflect the current like status
        if (isLiked) {
            likeButton.setText("Unlike (" + post.getLikeCount() + ")");
        } else {
            likeButton.setText("Like (" + post.getLikeCount() + ")");
        }
    }


    public void handlePostReport(Post post, String reason) {
        model.reportPost(currentUser, post, reason);
        updateStatisticsInView(mainFrame.getHomeContent().getRightSidebar());
    }
    
    public void handleCommentReport(Comment comment, String reason) {
        model.reportComment(currentUser, comment, reason);
    }


    public void filterPostsByAuthorAndUpdateView(String author) {
        ArrayList<Post> filteredPosts;
        if (author.equals("All Users") || author.isEmpty()) {
            filteredPosts = model.getPosts();
        } else {
            // Filter logic here
            filteredPosts = model.filterPostsByAuthor(author); // ... filter posts by author ...
        }
        mainFrame.getHomeContent().updateMiddleGrid(filteredPosts);
    }

    public void sortPostsByDateAndUpdateView() {
        ArrayList<Post> sortedPosts = model.sortPostsByDate();
        mainFrame.getHomeContent().updateMiddleGrid(sortedPosts);
    }

    public void sortPostsByPopularityAndUpdateView() {
        ArrayList<Post> sortedPosts = model.sortPostsByPopularity();
        mainFrame.getHomeContent().updateMiddleGrid(sortedPosts);
    }

    public MainPlatformFrame getMainFrame() {
        return mainFrame;
    }
    public void updateStatisticsInView(RightSidebar rightSidebar) {
        Map<String, Object> stats = generateStatistics();
        rightSidebar.updateStatistics(stats);
    }

    public void deletePost(Post post) {
        // Get the author of the post
        User postAuthor = post.getAuthor();

        // Delete the post from the model
        model.deletePost(post);

        // Check if the postAuthor has any remaining posts
        if (model.getUserPostCount(postAuthor) == 0) {
            // If not, delete the user
            model.deleteUser(postAuthor);
        }

        // Update the UI as needed
        // For example, you might refresh the post list in the main content view
        updateMiddleGrid();
    }

    public boolean isAdmin(User user) {
        return model.isAdmin(user);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void switchToSettingsPanel() {
    }

    public void switchToHomePanel() {
        updateMiddleGrid();
        refreshUserListInView();

    }

    public void logout() {

    }

    public void switchToProfilePanel() {
    }

    public void addComment(Comment comment, Post post) {
        // Add the comment to the data storage manager
        model.addComment(comment);

        // Add the comment to the specific post
        post.addComment(comment);

        // Update the UI after adding a comment
        updateMiddleGrid();
    }
    public void upvoteComment(Comment comment) {
        // Assuming you have a method to check if the user has already upvoted or downvoted
        if (!comment.hasUpvoted(getCurrentUser())) {
            comment.upvote(getCurrentUser()); // Implement the upvote method in the Comment class
        }
        updateMiddleGrid();
    }

    // Method to downvote a comment
    public void downvoteComment(Comment comment) {
        // Assuming you have a method to check if the user has already upvoted or downvoted
        if (!comment.hasDownvoted(getCurrentUser())) {
            comment.downvote(getCurrentUser()); // Implement the downvote method in the Comment class
        }
        updateMiddleGrid();
    }


    public ArrayList<Comment> getCommentsForPost(Post post) {
        return model.getCommentsForPost(post);
    }
}
