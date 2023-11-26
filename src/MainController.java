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
        mainFrame.getHomeContent().updateMiddleGrid(getPosts());
    }

    public void refreshUserListInView() {
        mainFrame.getHomeContent().getLeftSidebar().updateUserList(getUsers());
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

    public void addComment(Comment comment) {
        // Add the comment to the data storage manager
        model.addComment(comment);

        // Assuming you want to update the UI after adding a comment, you can call the following method
        updateMiddleGrid(); // or any other method you use to update the UI
    }

    public ArrayList<Comment> getCommentsForPost(Post post) {
        return model.getCommentsForPost(post);
    }
}
