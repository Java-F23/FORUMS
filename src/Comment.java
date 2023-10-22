class Comment {
    private int commentID;
    private Post post; // Reference to the associated post
    private User author;
    private String content;
    private String timestamp;
    private int upvotes;
    private int downvotes;

    public Comment(int commentID, Post post, User author, String content) {
        this.commentID = commentID;
        this.post = post;
        this.author = author;
        this.content = content;
        this.timestamp = getCurrentTimestamp();
        this.upvotes = 0;
        this.downvotes = 0;
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

    public String getCurrentTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
        return  downvotes;
    }
}
