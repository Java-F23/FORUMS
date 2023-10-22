import java.io.*;
import java.util.List;
import java.util.ArrayList;

class DataStorageManager {
    private List<User> users = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<AdministrativeAction> adminActions = new ArrayList<>();

    public List<User> readUserDataFromFile() {
        return users;
    }

    public List<Post> searchPosts(String keyword) {
        List<Post> matchingPosts = new ArrayList<>();

        for (Post post : posts) {
            if (post.getTitle().contains(keyword) || post.getContent().contains(keyword)) {
                matchingPosts.add(post);
            }
        }

        return matchingPosts;
    }

    public void writeUserDataToFile(List<User> users) {
        this.users = users;
    }

    public void storePostData(Post post) {
        posts.add(post);
    }

    public void storeCommentData(Comment comment) {
        comments.add(comment);
    }

    public void editPost(Post post, String newTitle, String newContent) {
        post.setTitle(newTitle);
        post.setContent(newContent);
    }

    public void editComment(Comment comment, String newContent) {
        comment.setContent(newContent);
    }

    public void deletePost(Post post) {
        posts.remove(post);
        // Remove associated comments
        for (Comment comment : post.getComments()) {
            comments.remove(comment);
        }
    }

    public void deleteComment(Comment comment) {
        // Ensure the comment is removed from its associated post
        if (comment.getPost() != null) {
            comment.getPost().deleteComment(comment);
        }
        comments.remove(comment);
    }

    public void storeAdminActionData(AdministrativeAction action) {
        adminActions.add(action);
    }

    public List<AdministrativeAction> readAdminActionDataFromFile() {
        return adminActions;
    }

    public List<Post> readAllPosts() {
        return posts;
    }
}
