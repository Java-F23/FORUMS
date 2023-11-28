import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

enum UserRole {
    NORMAL_USER, ADMIN
}

class User {
    private static int nextUserID = 1;
    private int userID;
    private String username;
    private String password;
    private UserRole userType;
    private Date createdAt;
    private ArrayList<Post> favoritePosts;

    public User(){

    }
    public User(String username, String password, UserRole userType) {
        this.userID = generateUniqueUserID();
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.createdAt = new Date();
        this.favoritePosts = new ArrayList<>();
    }

    public void addFavoritePost(Post post) {
        favoritePosts.add(post);
    }

    public void removeFavoritePost(Post post) {
        favoritePosts.remove(post);
    }

    public ArrayList<Post> getFavoritePosts() {
        return favoritePosts;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    private static int generateUniqueUserID() {
        int uniqueID = nextUserID;
        nextUserID++;
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

    public UserRole getUserType() {
        return userType;
    }

    public void setUserType(UserRole type) {
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

    @Override
    public String toString() {
        return "User ID: " + userID +
                "\nUsername: " + username +
                "\nUser Type: " + userType +
                "\nCreated At: " + createdAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return Objects.equals(username, other.username);
    }

    public boolean isAdmin() {
        return userType == UserRole.ADMIN;
    }
}
