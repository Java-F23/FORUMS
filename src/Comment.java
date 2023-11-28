import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Comment {
    private static int nextCommentID = 1;
    private int commentID;
    private Post post;
    private User author;
    private String content;
    private Date timestamp;
    private int upvotes;
    private int downvotes;

    private Set<User> upvotedUsers;
    private Set<User> downvotedUsers;

    public Comment(){}
    public Comment(Post post, User author, String content) {
        this.commentID = generateUniqueCommentID();
        this.post = post;
        this.author = author;
        this.content = content;
        this.timestamp = new Date();
        this.upvotes = 0;
        this.downvotes = 0;
        this.upvotedUsers = new HashSet<User>();
        this.downvotedUsers = new HashSet<User>();
    }

    private static int generateUniqueCommentID() {
        int uniqueID = nextCommentID;
        nextCommentID++;
        return uniqueID;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public void upvote(User user) {
        upvotes++;
        upvotedUsers.add(user);
    }

    public void downvote(User user) {
        downvotes++;
        downvotedUsers.add(user);
    }

    public boolean hasUpvoted(User user) {
        return upvotedUsers.contains(user);
    }

    public boolean hasDownvoted(User user) {
        return downvotedUsers.contains(user);
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentID == comment.commentID;
    }
    @Override
    public int hashCode() {
        return Objects.hash(commentID);
    }



}

