import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

class Comment {
    private static int nextCommentID = 1;
    private int commentID;
    private Post post;
    private User author;
    private String content;
    private Date timestamp;
    private int upvotes;
    private int downvotes;

    public Comment(){}
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
        nextCommentID++;
        return uniqueID;
    }

    public void upvote(User user) {
        if (user != null) {
            upvotes++;
        }
    }

    public void downvote(User user) {
        if (user != null) {
            downvotes++;
        }
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

    public Date getTimestamp() {
        return timestamp;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    @Override
    public String toString() {
        return "Comment ID: " + commentID + "\n" +
                "Author: " + author.getUsername() + "\n" +
                "Content: " + content + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Upvotes: " + upvotes + "\n" +
                "Downvotes: " + downvotes + "\n";
    }
}
