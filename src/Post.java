import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.Date;


class Post {
    private int postID;
    private String title;
    private String content;
    private User author;
    private final Date timestamp;
    private int viewCount;
    private int likeCount;
    private List<Comment> comments;

    private static int nextPostID = 1;


    public Post(String title, String content, User author) {
        comments = new ArrayList<Comment>();
        this.postID = generateUniquePostID();
        this.title = title;
        this.content = content;
        this.author = author;
        this.timestamp = new Date(); // Use the current date and time
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

    public Date getTimestamp() {
        return timestamp;
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

    public void incrementViewCount() {
        viewCount++;
    }

    public void incrementLikeCount() {
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



}