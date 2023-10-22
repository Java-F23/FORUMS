import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

class Post {
    private int postID;
    private String title;
    private String content;
    private User author;
    private String timestamp;
    private int viewCount;
    private int likeCount;
    private int upvotes;
    private int downvotes;

    private List<Comment> comments;

    private static int nextPostID = 1;


    public Post(String title, String content, User author) {
        comments = new ArrayList<Comment>();
        this.postID = generateUniquePostID();
        this.title = title;
        this.content = content;
        this.author = author;
        this.timestamp = TimestampUtil.getCurrentTimestamp(); // Use the TimestampUtil class for timestamp
    }


    public int getPostID() {
        return postID;
    }

    public void setPostID(int id) {
        this.postID = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int count) {
        this.viewCount = count;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int count) {
        this.likeCount = count;
    }

    public void updateViewCount() {
        // Implement code to update view count
        viewCount++;
    }

    public void updateLikeCount() {
        // Implement code to update like count
        likeCount++;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        // Implement code to add a comment to the post
        comments.add(comment);
    }
    public void deleteComment(Comment comment) {
        Iterator<Comment> iterator = comments.iterator();
        while (iterator.hasNext()) {
            Comment c = iterator.next();
            if (c.equals(comment)) {
                iterator.remove();
            }
        }
    }



    private int generateUniquePostID() {
        int uniqueID = nextPostID;
        nextPostID++; // Increment for the next post
        return uniqueID;
    }


    public void upvote() {
        upvotes++;
    }

    public void downvote() {
        downvotes++;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

}