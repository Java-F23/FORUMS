import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

class Comment {
    private static int nextCommentID = 1; // Initialize to 1, increment for each new user
    private int commentID;
    private Post post; // Reference to the associated post
    private User author;
    private String content;
    private Date timestamp;
    private int upvotes;
    private int downvotes;

    public Comment(Post post, User author, String content) {
        this.commentID = generateUniqueCommentID();
        this.post = post;
        this.author = author;
        this.content = content;
        this.timestamp = new Date();
        this.upvotes = 0;
        this.downvotes = 0;
    }

    private static int generateUniqueCommentID() {
        int uniqueID = nextCommentID;
        nextCommentID++; // Increment for the next user
        return uniqueID;
    }


    // Getters and setters for attributes

    public void upvote() {
        upvotes++;
    }

    public void downvote() {
        downvotes++;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int id) {
        this.commentID = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCurrentTimestamp() {
        return timestamp;
    }


    public void saveToFile() {
        // Implement code to save comment data to a file
    }

    public void readFromFile() {
        // Implement code to read comment data from a file
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }
}
