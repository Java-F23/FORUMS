import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

public class MainController {
    private MainPlatformFrame mainFrame;
    private DataStorageManager model;
    private User currentUser = new User("testUser", "testPassword", UserRole.ADMIN);
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

    public void toggleLikePost(Post post, JButton likeButton) {
        // Update the like count of the post in the model
        model.likePost(currentUser, post); // assuming model.toggleLike() returns new like status
        mainFrame.getHomeContent().refreshLikes(post); // Now just refreshes the UI for this post
    }

    public void filterPostsByAuthorAndUpdateView(String author) {
        ArrayList<Post> filteredPosts;
        if (author.equals("All Users") || author.isEmpty()) {
            // Handle the case where no filter is intended
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
}
