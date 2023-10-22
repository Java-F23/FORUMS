import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;


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

    private List<Comment> comments; // Store comments associated with the post
    private static int nextPostID = 1; // Initialize to 1, increment for each new post


    public Post() {
        // Constructor
        comments = new ArrayList<Comment>();

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


    public Post createPost(String title, String content, User author) {
        int newPostID = generateUniquePostID();

        String timestamp = getCurrentTimestamp();

        Post newPost = new Post();
        newPost.setPostID(newPostID);
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setAuthor(author);
        newPost.setTimestamp(timestamp);

        // Save the new post to your data storage mechanism

        return newPost;
    }

    private int generateUniquePostID() {
        int uniqueID = nextPostID;
        nextPostID++; // Increment for the next post
        return uniqueID;
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
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