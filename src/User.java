import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

class User {
    private static int nextUserID = 1; // Initialize to 1, increment for each new user
    private int userID;
    private String username;
    private String password;
    private String userType;
    private final Date createdAt;

    private List<Post> favoritePosts; // Store favorite posts

    public User(String username, String password, String userType) {
        this.userID = generateUniqueUserID();
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.createdAt = new Date();
        this.favoritePosts = new ArrayList<>(); // Initialize the list of favorite posts
    }

    public void addFavoritePost(Post post) {
        // Add the post to the list of favorite posts
        favoritePosts.add(post);
    }

    public void removeFavoritePost(Post post) {
        // Remove the post from the list of favorite posts
        favoritePosts.remove(post);
    }

    public List<Post> getFavoritePosts() {
        // Retrieve the list of favorite posts
        return favoritePosts;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    private static int generateUniqueUserID() {
        int uniqueID = nextUserID;
        nextUserID++; // Increment for the next user
        return uniqueID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int id) {
        this.userID = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String type) {
        this.userType = type;
    }

    public void editPost(Post post, String newContent) {
        if (this.equals(post.getAuthor())) {
            post.setContent(newContent);
        }
    }

    public void editComment(Comment comment, String newContent) {
        if (this.equals(comment.getAuthor())) {
            comment.setContent(newContent);
        }
    }


}
